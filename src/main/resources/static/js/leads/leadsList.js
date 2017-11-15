$(document).ready(function() {
    var table = $('#leadList').DataTable( {
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
        var csrfToken = $("meta[name='_csrf']").attr("content");
        if (confirm("Are you sure you wish to delete this record?")) {
          var id = thisRecord.data("id");
          $.ajax({
                  url: '/lead/records/' + id,
                  type: 'DELETE',
                  headers: {
                    "X-CSRF-TOKEN":csrfToken,
                  },
                  success: function(result) {
                      var table = $('#leadList').DataTable();
                      var row = thisRecord.parents('tr');
                      table.row(row)
                           .remove()
                           .draw();
                  }
              });
        }
    });
});
