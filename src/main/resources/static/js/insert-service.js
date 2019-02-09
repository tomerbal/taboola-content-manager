function initInsert(){
    $('#insertForm').submit(function (event) {
        event.preventDefault();
        var data = {};
        var insertInput = $(".insert-input");
        insertInput.each(function () {
            data[$(this).attr("name")] = $(this).val().length > 0 ? $(this).val() : $(this).attr("val");
        });
        $.ajax({
            url: '/content-item/create',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            cache: false,
            success: function () {
                getAllData(0);
                insertInput.each(function () {
                    $(this).val("");
                });
                $("#genericMessage").text("Item created successfully");
                $("#genericCode").text(200);
                $("#genericModal").modal();
            },
            error: function (data) {
                $("#genericMessage").text("There was an error inserting the item");
                $("#genericCode").text(data.status);
                $("#genericModal").modal();
            }
        });
    });
}