$(document).ready(function() {
    var table = $('#accountList').DataTable( {
        scrollY:        '50vh',
        scrollCollapse: true,
        paging:         false
    } );
} );

$(document).ready(function() {
    $(".glyphicon-trash").click(function(event) {
        var thisRecord = $(this);
        if (confirm("Are you sure you wish to delete this record?")) {
            var id = thisRecord.data("id");
            $.ajax({
                url: '/account/records/' + id,
                type: 'DELETE',
                success: function(result) {
                    var table = $('#accountList').DataTable();
                    var row = thisRecord.parents('tr');
                    table.row(row)
                        .remove()
                        .draw();
                }
            });
        }
    });
});
