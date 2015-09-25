import React from 'react';
import NoteList from './NoteList';
import NoteApiUtils from '../utils/NoteApiUtils.js';
import logError from '../utils/logError'

class NoteListContainer extends React.Component {
  constructor() {
    super();
    this.state = { notes: [] }
  }
  componentDidMount() {
    var user = {name: 'xx'};
    NoteApiUtils.fetchNotesForUser(user)
      .then(function(json) {
        this.setState({notes: json});
    }.bind(this))
      .catch(logError);
  }
  render() {
    return <NoteList notes={this.state.notes} />;
  }
}

export default NoteListContainer;