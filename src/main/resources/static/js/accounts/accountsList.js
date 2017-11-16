$(document).ready(function () {
    var table = $('#accountList').DataTable({
        scrollY: '50vh',
        scrollCollapse: true,
        paging: false
    });
});

$(document).ready(function () {
    $(".glyphicon-trash").click(function (event) {
        var thisRecord = $(this);
        var csrfToken = $("meta[name='_csrf']").attr("content");
        if (confirm("Are you sure you wish to delete this record?")) {
            var id = thisRecord.data("id");
            $.ajax({
                url: '/account/records/' + id,
                type: 'DELETE',
                headers: {
                    "X-CSRF-TOKEN": csrfToken,
                },
                success: function (result) {
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