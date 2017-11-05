$(document).ready(function() {
    $("#deleteRecord").click(function(event) {
        var thisRecord = $(this);
        if (confirm("Are you sure you wish to delete this record?")) {
          var id = thisRecord.data("id");
          $.ajax({
                  url: '/lead/records/' + id,
                  type: 'DELETE',
                  success: function(result) {
                    var hostName = window.location.hostname;
                    window.location.replace("/lead/");
                  }
              });
        }
    });
});
