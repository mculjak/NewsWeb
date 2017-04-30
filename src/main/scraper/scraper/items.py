from scrapy.item import Item, Field

class NewswebItem(Item):
    url = Field()
    html = Field()
