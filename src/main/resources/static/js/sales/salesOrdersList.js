$(document).ready(function() {
  readIntoTable("#salesOrderList");
  tableDelete('/salesorder/records/', "#salesOrderList");
});
