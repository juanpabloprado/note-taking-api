// index.jsx

import 'whatwg-fetch';
import NotesApp from './components/NotesApp';
import React from 'react';

React.render(
  <NotesApp />,
  document.querySelector('.js-react-app')
);