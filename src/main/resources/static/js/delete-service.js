function deleteItem(element) {
    var $this = $(element);
    var itemId = $this.closest("tr").attr("itemId");
    $("#deleteItemId").attr("value", itemId);
    $('#deleteForm').submit(function (event) {
        event.preventDefault();
        var data = {itemId: itemId};
        $.ajax({
            url: '/content-item/delete',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            cache: false,
            success: function () {
                $('#deleteItemModal').modal('hide');
                $("#genericMessage").text("Item deleted successfully");
                $("#genericCode").text(200);
                $("#genericModal").modal();
            },
            error: function (data) {
                $("#genericMessage").text("There was an error deleting the item");
                $("#genericCode").text(data.status);
                $("#genericModal").modal();
            }
        });
    });
}