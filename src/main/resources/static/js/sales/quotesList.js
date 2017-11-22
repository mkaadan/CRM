$(document).ready(function() {
  readIntoTable("#quoteList");
  tableDelete('/quote/records/', "#quoteList");
});
