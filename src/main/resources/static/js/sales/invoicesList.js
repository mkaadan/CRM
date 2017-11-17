$(document).ready(function() {
    var table = $('#invoiceList').DataTable( {
        scrollY:        "300px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         false,
        fixedColumns:   {
            heightMatch: 'none'
        }
    } );
} );

$(document).ready(function() {
    $(".glyphicon-trash").click(function(event) {
        var thisRecord = $(this);
        if (confirm("Are you sure you wish to delete this record?")) {
          var id = thisRecord.data("id");
          $.ajax({
                  url: '/invoice/records/' + id,
                  type: 'DELETE',
                  success: function(result) {
                      var table = $('#invoiceList').DataTable();
                      var row = thisRecord.parents('tr');
                      table.row(row)
                           .remove()
                           .draw();
                  }
              });
        }
    });
});
