# -*- coding: utf-8 -*-
from scraper.spiders.base_spider import BaseSpider


class ArstechnicaSpider(BaseSpider):
    name = "arstechnica"
    allowed_domains = ["arstechnica.com"]
    start_urls = (
        'https://arstechnica.com/gadgets/',
        'https://arstechnica.com/information-technology/',
        'https://arstechnica.com/business/',
        'https://arstechnica.com/tech-policy/',
        'https://arstechnica.com/gaming/',
        'https://arstechnica.com/science/',
        'https://arstechnica.com/security/',
    )

    def get_url_xpath(self):
        return '//article/a/@href'
