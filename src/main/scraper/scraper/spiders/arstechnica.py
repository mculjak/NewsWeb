# -*- coding: utf-8 -*-
from scraper.spiders.base_spider import BaseSpider


class ArstechnicaSpider(BaseSpider):
    name = "arstechnica"
    allowed_domains = ["arstechnica.com"]
    start_urls = (
        'http://arstechnica.com/gadgets/',
        'http://arstechnica.com/information-technology/',
        'http://arstechnica.com/business/',
        'http://arstechnica.com/tech-policy/',
        'http://arstechnica.com/gaming/',
        'http://arstechnica.com/science/',
        'http://arstechnica.com/security/',
    )

    def get_url_xpath(self):
        return '//article/a/@href'
