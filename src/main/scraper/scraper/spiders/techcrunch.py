# -*- coding: utf-8 -*-
from scraper.spiders.base_spider import BaseSpider


class TechcrunchSpider(BaseSpider):
    name = 'techcrunch'
    allowed_domains = ['techcrunch.com', 'feeds.feedburner.com', 'feedproxy.google.com']
    start_urls = ['https://feeds.feedburner.com/TechCrunch/']

    def get_url_xpath(self):
        return '//item/link/text()'

