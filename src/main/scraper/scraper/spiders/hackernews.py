# -*- coding: utf-8 -*-
from scraper.spiders.base_spider import BaseSpider


class HackernewsSpider(BaseSpider):
    name = "hackernews"
    allowed_domains = []
    start_urls = (
        'https://news.ycombinator.com/',
    )

    def get_url_xpath(self):
        return '//td[@class="title"]/a/@href'
