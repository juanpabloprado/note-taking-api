import React from 'react';
import NoteList from './NoteList';
import $ from 'jquery';

class NoteListContainer extends React.Component {
  constructor() {
    super();
    this.state = { notes: [] }
  }
  componentDidMount() {
    $.ajax({
      url: "http://localhost:8080/v1/users/xx/notes",
      dataType: 'json',
      beforeSend: function (xhr) {
        xhr.setRequestHeader("Authorization", "Bearer " + "f5d473d6-8a4b-4323-ad43-bac735520a40");
      },
      success: function(notes) {
        this.setState({notes: notes});
      }.bind(this)
    });
  }
  render() {
    return <NoteList notes={this.state.notes} />;
  }
}

export default NoteListContainer;