$(document).ready(function() {
    var table = $('#productList').DataTable( {
        scrollY:        "300px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         false,
        fixedColumns:   {
            heightMatch: 'none'
        }
    } );
} );
