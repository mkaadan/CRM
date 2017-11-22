$(document).ready(function () {
    readIntoTable("#contractList");
    tableDelete('/contract/records/', "#contractList");
});