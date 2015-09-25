import _ from 'lodash';
import checkResults from './checkResults';
import xhr from './xhr';

export default {

  fetchNotesForUser(user) {
    return xhr(`/v1/users/${user.name}/notes`).then(checkResults);
  },

};
