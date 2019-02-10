function editItem(element){
    var $this = $(element);
    var tr =  $this.closest("tr");
    var itemId = tr.attr("itemId");
    var country = tr.find(".country").text();
    var brand = tr.find(".brand").text();
    var title = tr.find(".title").text();
    var img = tr.find(".img img").attr("src");

    $("#countryInputEdit").attr("val", country);
    $('#countryChooserEdit').select2({
        placeholder: country
    });

    $("#brandInputEdit").attr("val", brand);
    $('#brandChooserEdit').select2({
        placeholder: brand
    });

    $("#imgEditInput").val(img);
    $("#titleEditInput").val(title);

    $('#editForm').submit(function (event) {
        event.preventDefault();
        var data = {itemId: itemId};
        var editInput = $(".edit-input");
        editInput.each(function () {
            data[$(this).attr("name")] = $(this).val().length > 0 ? $(this).val() : $(this).attr("val");
        });
        console.log(data);
        $.ajax({
            url: '/content-item/edit',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            cache: false,
            success: function () {
                getAllData(0);
                editInput.each(function () {
                    $(this).val("");
                });
                $("#editItemModal").modal('hide');
                $("#genericMessage").text("Item saved successfully");
                $("#genericCode").text(200);
                $("#genericModal").modal();
                config.chosenItems = {};
            },
            error: function (data) {
                $("#genericMessage").text("There was an error editing the item");
                $("#genericCode").text(data.status);
                $("#genericModal").modal();
            }
        });
    });
}