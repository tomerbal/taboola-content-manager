function initWidget() {

    $('#widgetModalTrigger').on("click", function () {
        var length = Object.keys(config.chosenItems).length;
        if (length < config.widgetSize){
            $("#genericMessage").text("Please choose " + config.widgetSize + " items");
            $("#genericCode").text();
            $("#genericModal").modal();
        }
        else{
            var tds = $("#widgetTable td");
            var i = 0;
            var itemsArray = [];
            for (var itemId in config.chosenItems) {
                var item = config.chosenItems[itemId];
                itemsArray.push(item);
            }
            tds.each(function(){
                $(this).find(".widget-image-wrapper img").attr("src", itemsArray[i].img);
                $(this).find("h5").text(itemsArray[i].title);
                $(this).find("h6").text(itemsArray[i].brand);
                i++;
            });
            $("#widgetModal").modal();
        }

    });
}

