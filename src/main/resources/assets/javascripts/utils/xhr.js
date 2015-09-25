const status = (response) => {
  if (response.status >= 200 && response.status < 300) {
    return Promise.resolve(response);
  } else {
    return Promise.reject(new Error(response.statusText));
  }
};

const json = (response) => {
  if (response.status !== 204) {
    return response.json();
  } else {
    return {};
  }
};

const xhr = (url, params = {}) => {
  params = Object.assign({
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + 'f5d473d6-8a4b-4323-ad43-bac735520a40'
    },
    credentials: 'same-origin'
  }, params);

  return fetch(url, params)
    .then(status)
    .then(json);
};

export default xhr;
