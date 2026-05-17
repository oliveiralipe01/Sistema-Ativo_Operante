const API_BASE = '';

function getToken() {
  return localStorage.getItem('token');
}

function getUsuario() {
  try {
    return JSON.parse(localStorage.getItem('usuario') || '{}');
  } catch {
    return {};
  }
}

function salvarLogin(resposta) {
  localStorage.setItem('token', resposta.token);
  localStorage.setItem('usuario', JSON.stringify({
    id: resposta.id,
    email: resposta.email,
    nivel: resposta.nivel
  }));
}

function logout() {
  localStorage.clear();
  window.location.href = 'index.html';
}

function extrairMensagemErro(data) {
  if (!data) {
    return 'Erro na requisição.';
  }

  if (typeof data === 'string') {
    return data;
  }

  if (data.mensagem) {
    return data.mensagem;
  }

  if (data.message) {
    return data.message;
  }

  if (data.error) {
    return data.error;
  }

  if (data.status && data.path) {
    return `Erro ${data.status} ao acessar ${data.path}`;
  }

  return JSON.stringify(data);
}

async function apiFetch(url, options = {}) {
  const headers = options.headers || {};

  if (!(options.body instanceof FormData)) {
    headers['Content-Type'] = 'application/json';
  }

  const token = getToken();

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(API_BASE + url, {
    ...options,
    headers
  });

  if (response.status === 204) {
    return null;
  }

  const text = await response.text();

  let data = null;

  try {
    data = text ? JSON.parse(text) : null;
  } catch {
    data = text;
  }

  if (!response.ok) {
    throw new Error(extrairMensagemErro(data));
  }

  return data;
}

function protegerPagina(nivelPermitido) {
  const usuario = getUsuario();

  if (!getToken() || !usuario.nivel) {
    window.location.href = 'index.html';
    return;
  }

  if (nivelPermitido && Number(usuario.nivel) !== Number(nivelPermitido)) {
    window.location.href = Number(usuario.nivel) === 1 ? 'admin.html' : 'cidadao.html';
  }
}

function formatarData(data) {
  if (!data) {
    return '-';
  }

  const [ano, mes, dia] = data.split('-');
  return `${dia}/${mes}/${ano}`;
}