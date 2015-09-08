import React from 'react';

class NoteList extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <ul> {this.props.notes.map(function ({title, content}) {
      return <li>{title}—{content}</li>;
    })} </ul>;
  }

}

export default NoteList;