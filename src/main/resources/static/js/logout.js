$(document).ready(function() {
    $("#logout").click(function(event) {
          var csrfToken = $("meta[name='_csrf']").attr("content");
          $.ajax({
                  url: '/logout',
                  type: 'POST',
                  headers: {
                    "X-CSRF-TOKEN":csrfToken,
                  },
                  success: function(result) {
                    var hostName = window.location.hostname;
                    window.location.replace("/login");
                  }
              });
    });
});
