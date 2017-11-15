$(document).ready(function() {
  readIntoTable("#userList");
  tableDelete('/admin/user/delete/', "#userList");
});
