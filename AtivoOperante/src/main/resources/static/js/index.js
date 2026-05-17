const tabs = document.querySelectorAll('.tab');
const formLogin = document.getElementById('formLogin');
const formCadastro = document.getElementById('formCadastro');
const mensagem = document.getElementById('mensagem');

tabs.forEach(tab => {
  tab.addEventListener('click', () => {
    tabs.forEach(t => t.classList.remove('active'));
    tab.classList.add('active');

    formLogin.classList.toggle('active-form', tab.dataset.tab === 'login');
    formCadastro.classList.toggle('active-form', tab.dataset.tab === 'cadastro');
    mensagem.textContent = '';
    mensagem.className = 'mensagem';
  });
});

formLogin.addEventListener('submit', async (event) => {
  event.preventDefault();
  mensagem.textContent = 'Entrando...';
  mensagem.className = 'mensagem';

  try {
    const resposta = await apiFetch('/acesso/login', {
      method: 'POST',
      body: JSON.stringify({
        email: document.getElementById('loginEmail').value,
        senha: Number(document.getElementById('loginSenha').value)
      })
    });

    if (!resposta || !resposta.token) throw new Error('Login inválido');

    salvarLogin(resposta);
    window.location.href = Number(resposta.nivel) === 1 ? 'admin.html' : 'cidadao.html';
  } catch (error) {
    mensagem.textContent = error.message;
    mensagem.className = 'mensagem erro';
  }
});

formCadastro.addEventListener('submit', async (event) => {
  event.preventDefault();
  mensagem.textContent = 'Cadastrando...';
  mensagem.className = 'mensagem';

  try {
    await apiFetch('/acesso/cadastrar', {
      method: 'POST',
      body: JSON.stringify({
        cpf: Number(document.getElementById('cadCpf').value),
        email: document.getElementById('cadEmail').value,
        senha: Number(document.getElementById('cadSenha').value),
        nivel: 2
      })
    });

    mensagem.textContent = 'Cadastro realizado. Agora faça login.';
    mensagem.className = 'mensagem sucesso';
    formCadastro.reset();
  } catch (error) {
    mensagem.textContent = error.message;
    mensagem.className = 'mensagem erro';
  }
});
