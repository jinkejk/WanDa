from distutils.core import setup
import py2exe

setup(console=['sms.py'],
        options={
            "py2exe": {
                "packages": ["email"]
                }
            })
