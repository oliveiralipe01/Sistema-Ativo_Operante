protegerPagina(2);

document.getElementById('sair').addEventListener('click', logout);
document.getElementById('recarregar').addEventListener('click', carregarDenuncias);
document.getElementById('data').valueAsDate = new Date();

const mensagem = document.getElementById('mensagem');
const formDenuncia = document.getElementById('formDenuncia');
const listaDenuncias = document.getElementById('listaDenuncias');

async function carregarCombos() {
  const [orgaos, tipos] = await Promise.all([
    apiFetch('/apis/cidadao/orgaos'),
    apiFetch('/apis/cidadao/tipos')
  ]);

  document.getElementById('orgao').innerHTML = orgaos.map(o => `<option value="${o.id}">${o.nome}</option>`).join('');
  document.getElementById('tipo').innerHTML = tipos.map(t => `<option value="${t.id}">${t.nome}</option>`).join('');
}

async function carregarDenuncias() {
  const usuario = getUsuario();

  if (!usuario.id) {
    listaDenuncias.innerHTML = '<p class="small-note">O login precisa retornar o id do usuário para listar suas denúncias.</p>';
    return;
  }

  const [denuncias, feedbacks] = await Promise.all([
    apiFetch(`/apis/cidadao/denuncias/${usuario.id}`),
    apiFetch('/apis/cidadao/feedbacks')
  ]);

  if (!denuncias.length) {
    listaDenuncias.innerHTML = '<p class="small-note">Você ainda não enviou denúncias.</p>';
    return;
  }

  listaDenuncias.innerHTML = denuncias.map(d => {
    const feedback = feedbacks.find(f => f.denuncia?.id === d.id);
    return `
      <article class="card-item">
        <h3>${d.titulo}</h3>
        <div class="card-meta">
          <span class="pill">Data: ${formatarData(d.data)}</span>
          <span class="pill">Urgência: ${d.urgencia}</span>
          <span class="pill">Órgão: ${d.orgao?.nome || '-'}</span>
          <span class="pill">Tipo: ${d.tipo?.nome || '-'}</span>
        </div>
        <p>${d.texto}</p>
        ${feedback ? `<div class="feedback-box"><strong>Feedback:</strong><p>${feedback.texto}</p></div>` : '<p class="small-note">Sem feedback ainda.</p>'}
      </article>
    `;
  }).join('');
}

formDenuncia.addEventListener('submit', async (event) => {
  event.preventDefault();
  const usuario = getUsuario();

  if (!usuario.id) {
    mensagem.textContent = 'Ajuste o backend para o login retornar o id do usuário.';
    mensagem.className = 'mensagem erro';
    return;
  }

  try {
    await apiFetch('/apis/cidadao/denuncia', {
      method: 'POST',
      body: JSON.stringify({
        titulo: document.getElementById('titulo').value,
        texto: document.getElementById('texto').value,
        urgencia: Number(document.getElementById('urgencia').value),
        data: document.getElementById('data').value,
        orgao: { id: Number(document.getElementById('orgao').value) },
        tipo: { id: Number(document.getElementById('tipo').value) },
        usuario: { id: Number(usuario.id) }
      })
    });

    mensagem.textContent = 'Denúncia enviada com sucesso.';
    mensagem.className = 'mensagem sucesso';
    formDenuncia.reset();
    document.getElementById('data').valueAsDate = new Date();
    await carregarDenuncias();
  } catch (error) {
    mensagem.textContent = error.message;
    mensagem.className = 'mensagem erro';
  }
});

carregarCombos().then(carregarDenuncias).catch(error => {
  mensagem.textContent = error.message;
  mensagem.className = 'mensagem erro';
});
