# Scrapy settings for newsweb project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/topics/settings.html
#

SPIDER_MODULES = ['scraper.spiders']
NEWSPIDER_MODULE = 'scraper.spiders'
DEFAULT_ITEM_CLASS = 'scraper.items.NewswebItem'
DOWNLOAD_DELAY = 0.75
ROBOTSTXT_OBEY = True
ITEM_PIPELINES = {
        'scraper.pipelines.NewswebPipeline': 100,
}
IGNORED_EXTENSIONS = [
        # images
        'mng', 'pct', 'bmp', 'gif', 'jpg', 'jpeg', 'png', 'pst', 'psp', 'tif',
        'tiff', 'ai', 'drw', 'dxf', 'eps', 'ps', 'svg',

        # audio
        'mp3', 'wma', 'ogg', 'wav', 'ra', 'aac', 'mid', 'au', 'aiff',

        # video
        '3gp', 'asf', 'asx', 'avi', 'mov', 'mp4', 'mpg', 'qt', 'rm', 'swf', 'wmv',
        'm4a',

        # other
        'css', 'pdf', 'doc', 'exe', 'bin', 'rss', 'zip', 'rar',
]

#DB_USER =
#DB_PASSWORD =
DB_NAME = 'newsweb'

NEWSWEB_ENDPOINT = 'http://localhost:8080/api/'
