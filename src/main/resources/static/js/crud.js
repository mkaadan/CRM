function readIntoTable(nameOfTable) {
    var table = $(nameOfTable).DataTable({
        scrollY: "400px",
        scrollX: true,
        scrollCollapse: true,
        paging: false,
        fixedColumns: {
            heightMatch: 'none'
        }
    });
}

function deleteRecord(nameOfId, deleteUri, redirectLocation) {
    $(nameOfId).click(function (event) {
        var thisRecord = $(this);
        var csrfToken = $("meta[name='_csrf']").attr("content");
        if (confirm("Are you sure you wish to delete this record?")) {
            var id = thisRecord.data("id");
            $.ajax({
                url: deleteUri + id,
                type: 'DELETE',
                headers: {
                    "X-CSRF-TOKEN": csrfToken,
                },
                success: function (result) {
                    var hostName = window.location.hostname;
                    window.location.replace(redirectLocation);
                }
            });
        }
    });
}

function tableDelete(deleteUri, tableName) {
    $(".glyphicon-trash").click(function (event) {
        var thisRecord = $(this);
        var csrfToken = $("meta[name='_csrf']").attr("content");
        if (confirm("Are you sure you wish to delete this record?")) {
            var id = thisRecord.data("id");
            $.ajax({
                url: deleteUri + id,
                type: 'DELETE',
                headers: {
                    "X-CSRF-TOKEN": csrfToken,
                },
                success: function (result) {
                    var table = $(tableName).DataTable();
                    var row = thisRecord.parents('tr');
                    table.row(row)
                        .remove()
                        .draw();
                }
            });
        }
    });
}
