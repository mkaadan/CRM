$(document).ready(function () {
    $("#deleteRecord").click(function (event) {
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
                    var hostName = window.location.hostname;
                    window.location.replace("/account/");
                }
            });
        }
    });
});