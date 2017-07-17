from flask import Flask, request
from multiprocessing import Pool, freeze_support
from logging.handlers import RotatingFileHandler
from serial.tools import list_ports
import serial
import logging
import sys


app = Flask(__name__)
gsm = b'AT+CSCS="GSM"\r'
txt = b'AT+CMGF=1\r'


def find_sms_tool():
    i = list_ports.grep(r'Prolific*')
    try:
        p = i.next()
        return p.device
    except StopIteration:
        return None


@app.before_first_request
def initialize():
    app.logger.info('Initial the pool')
    app.pool = Pool(processes=1)
    app.logger.info('Initial the pool finished')
    app.logger.info('Begin to check sms console')
    sms = find_sms_tool()
    app.logger.info('Sms port is %s' % (sms,))
    app.sms = sms


def send(sms, cellphone, content):
    app.logger.info('Begin to send sms')
    print "hello"
    mobile = b'AT+CMGS="%s"\r' % (cellphone.encode('latin-1'))
    print mobile
    t = serial.Serial(sms, 9600, bytesize=serial.EIGHTBITS, stopbits=serial.STOPBITS_ONE, timeout=5)
    app.logger.info('Serial is open: %b', t.isOpen())
    n = t.write(gsm)
    #app.logger.info(t.read(n))
    print t.read(n)
    n = t.write(txt)
    app.logger.info(t.read(n))
    n = t.write(mobile)
    app.logger.info(t.read(n))
    t.write(content.encode('latin-1'))
    t.write(b'1A'.decode('hex'))
    t.close()


@app.route('/sms', methods=['POST'])
def send_sms():
    mobile = request.form['mobile']
    content = request.form['content']
    app.pool.apply_async(send, (app.sms, mobile, content,))
    return "ok"




if __name__ == '__main__':
    freeze_support()
    handler = RotatingFileHandler('sms.log', maxBytes=10000, backupCount=1)
    handler.setLevel(logging.INFO)
    app.logger.addHandler(handler)

    if not find_sms_tool():
        app.logger.error('Cannot find sms console')
        sys.exit(1)

    app.run(debug=True, host='0.0.0.0')
