from flask import Flask, request, g
import serial
import logging


app = Flask(__name__)
gsm = b'AT+CSCS="GSM"\r'
txt = b'AT+CMGF=1\r'



@app.route('/hello')
def hello():
    return 'hello'


def send(cellphone, content):
    app.logger.info('Begin to send sms')
    mobile = b'AT+CMGS="%s"\r' % (cellphone.encode('latin-1'))
    t = serial.Serial('COM6', 9600, bytesize=serial.EIGHTBITS, stopbits=serial.STOPBITS_ONE, timeout=5)
    app.logger.info('Serial is open: %b', t.isOpen())
    n = t.write(gsm)
    app.logger.info(t.read(n))
    n = t.write(txt)
    app.logger.info(t.read(n))
    n = t.write(mobile)
    app.logger.info(t.read(n))
    t.write(content.encode('latin-1'))
    t.write(b'1A'.decode('hex'))
    t.close()


@app.route('/sms', methods=['POST'])
def send_sms():
    print 'here'
    mobile = request.form['mobile']
    content = request.form['content']
    return "ok"


if __name__ == '__main__':

    app.run(debug=True, host='0.0.0.0')
