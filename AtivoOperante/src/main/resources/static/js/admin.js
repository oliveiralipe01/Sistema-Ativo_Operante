protegerPagina(1);

const mensagemAdmin = document.createElement('p');
mensagemAdmin.className = 'mensagem';
document.querySelector('.topbar').insertAdjacentElement('afterend', mensagemAdmin);

document.getElementById('sair').addEventListener('click', logout);
document.getElementById('recarregar').addEventListener('click', carregarDenuncias);

document.getElementById('fecharModal').addEventListener('click', () => {
    document.getElementById('modalFeedback').close();
});

function mostrarMensagem(texto, tipo = '') {
    mensagemAdmin.textContent = texto;
    mensagemAdmin.className = `mensagem ${tipo}`;
}

async function carregarOrgaos() {
    try {
        const orgaos = await apiFetch('/apis/adm/allOrgaos');
        const area = document.getElementById('listaOrgaos');

        if (!orgaos || !orgaos.length) {
            area.innerHTML = '<p class="small-note">Nenhum órgão cadastrado.</p>';
            return;
        }

        area.innerHTML = orgaos.map(o => `
      <div class="row-item">
        <strong>${o.nome}</strong>
        <div class="row-actions">
          <button class="btn-small btn-secondary" data-id="${o.id}" data-nome="${o.nome}" onclick="editarOrgaoBotao(this)">Editar</button>
          <button class="btn-small btn-danger" onclick="excluirOrgao(${o.id})">Excluir</button>
        </div>
      </div>
    `).join('');
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
}

async function carregarTipos() {
    try {
        const tipos = await apiFetch('/apis/adm/allTipos');
        const area = document.getElementById('listaTipos');

        if (!tipos || !tipos.length) {
            area.innerHTML = '<p class="small-note">Nenhum tipo cadastrado.</p>';
            return;
        }

        area.innerHTML = tipos.map(t => `
      <div class="row-item">
        <strong>${t.nome}</strong>
        <div class="row-actions">
          <button class="btn-small btn-secondary" data-id="${t.id}" data-nome="${t.nome}" onclick="editarTipoBotao(this)">Editar</button>
          <button class="btn-small btn-danger" onclick="excluirTipo(${t.id})">Excluir</button>
        </div>
      </div>
    `).join('');
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
}

async function carregarDenuncias() {
    try {
        const denuncias = await apiFetch('/apis/adm/denuncias');
        const feedbacks = await apiFetch('/apis/cidadao/feedbacks');
        const area = document.getElementById('listaDenuncias');

        if (!denuncias || !denuncias.length) {
            area.innerHTML = '<p class="small-note">Nenhuma denúncia cadastrada.</p>';
            return;
        }

        area.innerHTML = denuncias.map(d => {
            const feedback = feedbacks.find(f => f.denuncia?.id === d.id);

            return `
        <article class="card-item">
          <h3>${d.titulo}</h3>

          <div class="card-meta">
            <span class="pill">ID: ${d.id}</span>
            <span class="pill">Data: ${formatarData(d.data)}</span>
            <span class="pill">Urgência: ${d.urgencia}</span>
            <span class="pill">Órgão: ${d.orgao?.nome || '-'}</span>
            <span class="pill">Tipo: ${d.tipo?.nome || '-'}</span>
            <span class="pill">Usuário: ${d.usuario?.email || '-'}</span>
          </div>

          <p>${d.texto}</p>

          ${feedback
                ? `<div class="feedback-box"><strong>Feedback já registrado:</strong><p>${feedback.texto}</p></div>`
                : '<p class="small-note">Sem feedback.</p>'
            }

          <div class="card-actions">
            <button class="btn-small" onclick="abrirFeedback(${d.id})" ${feedback ? 'disabled' : ''}>Dar feedback</button>
            <button class="btn-small btn-danger" onclick="excluirDenuncia(${d.id})">Excluir denúncia</button>
          </div>
        </article>
      `;
        }).join('');
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
}

document.getElementById('formOrgao').addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = document.getElementById('orgaoId').value;
    const nome = document.getElementById('orgaoNome').value.trim();

    if (!nome) {
        mostrarMensagem('Informe o nome do órgão.', 'erro');
        return;
    }

    try {
        await apiFetch(id ? '/apis/adm/alterOrgao' : '/apis/adm/saveOrgao', {
            method: id ? 'PUT' : 'POST',
            body: JSON.stringify({
                id: id ? Number(id) : null,
                nome: nome
            })
        });

        event.target.reset();
        document.getElementById('orgaoId').value = '';

        mostrarMensagem(id ? 'Órgão alterado com sucesso.' : 'Órgão cadastrado com sucesso.', 'sucesso');
        await carregarOrgaos();
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
});

document.getElementById('formTipo').addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = document.getElementById('tipoId').value;
    const nome = document.getElementById('tipoNome').value.trim();

    if (!nome) {
        mostrarMensagem('Informe o nome do tipo.', 'erro');
        return;
    }

    try {
        await apiFetch(id ? '/apis/adm/alterTipo' : '/apis/adm/saveTipo', {
            method: id ? 'PUT' : 'POST',
            body: JSON.stringify({
                id: id ? Number(id) : null,
                nome: nome
            })
        });

        event.target.reset();
        document.getElementById('tipoId').value = '';

        mostrarMensagem(id ? 'Tipo alterado com sucesso.' : 'Tipo cadastrado com sucesso.', 'sucesso');
        await carregarTipos();
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
});

function editarOrgaoBotao(botao) {
    document.getElementById('orgaoId').value = botao.dataset.id;
    document.getElementById('orgaoNome').value = botao.dataset.nome;
    mostrarMensagem('Editando órgão. Altere o nome e clique em Salvar.', '');
}

function editarTipoBotao(botao) {
    document.getElementById('tipoId').value = botao.dataset.id;
    document.getElementById('tipoNome').value = botao.dataset.nome;
    mostrarMensagem('Editando tipo. Altere o nome e clique em Salvar.', '');
}

async function excluirOrgao(id) {
    if (!confirm('Excluir este órgão?')) {
        return;
    }

    try {
        await apiFetch(`/apis/adm/orgao/${id}`, {
            method: 'DELETE'
        });

        mostrarMensagem('Órgão excluído com sucesso.', 'sucesso');
        await carregarOrgaos();
    } catch (error) {
        mostrarMensagem('Não foi possível excluir o órgão. Verifique se ele está sendo usado em alguma denúncia.', 'erro');
    }
}

async function excluirTipo(id) {
    if (!confirm('Excluir este tipo?')) {
        return;
    }

    try {
        await apiFetch(`/apis/adm/tipo/${id}`, {
            method: 'DELETE'
        });

        mostrarMensagem('Tipo excluído com sucesso.', 'sucesso');
        await carregarTipos();
    } catch (error) {
        mostrarMensagem('Não foi possível excluir o tipo. Verifique se ele está sendo usado em alguma denúncia.', 'erro');
    }
}

async function excluirDenuncia(id) {
    if (!confirm('Excluir esta denúncia?')) {
        return;
    }

    try {
        await apiFetch(`/apis/adm/denuncia/${id}`, {
            method: 'DELETE'
        });

        mostrarMensagem('Denúncia excluída com sucesso.', 'sucesso');
        await carregarDenuncias();
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
}

function abrirFeedback(id) {
    document.getElementById('feedbackDenunciaId').value = id;
    document.getElementById('feedbackTexto').value = '';
    document.getElementById('modalFeedback').showModal();
}

document.getElementById('formFeedback').addEventListener('submit', async (event) => {
    event.preventDefault();

    try {
        await apiFetch('/apis/adm/feedback', {
            method: 'POST',
            body: JSON.stringify({
                texto: document.getElementById('feedbackTexto').value,
                denuncia: {
                    id: Number(document.getElementById('feedbackDenunciaId').value)
                }
            })
        });

        document.getElementById('modalFeedback').close();
        mostrarMensagem('Feedback registrado com sucesso.', 'sucesso');
        await carregarDenuncias();
    } catch (error) {
        mostrarMensagem(error.message, 'erro');
    }
});

Promise.all([
    carregarOrgaos(),
    carregarTipos(),
    carregarDenuncias()
]);