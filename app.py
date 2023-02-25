from flask import Flask, request, jsonify, render_template
import pickle
import numpy

app = Flask(__name__, template_folder='./templates/', static_folder='./templates/static/')
model = pickle.load(open('./model/model.pkl','rb'))

@app.route('/')
def home():
    return render_template("index.html")

@app.route('/predict', methods=['POST'])
def predict():
    cmpn = str.capitalize((request.form.get('company')))
    cpu = str(request.form.get('cpu'))
    ram = int(request.form.get('ram'))
    gpu = str(request.form.get('gpu'))
    opsys = str(request.form.get('opsys'))
    weight = float(request.form.get('weight'))
    hdd = int(request.form.get('hdd'))
    ssd = int(request.form.get('ssd'))

    kumpulan_data_queri = numpy.array([[cmpn,cpu,ram,gpu,opsys,weight,hdd,ssd]])
    harga      = int(numpy.exp(model.predict(kumpulan_data_queri)[0]))
    konversi_harga      = harga*16204
    prediction = int(konversi_harga)
    return jsonify({'harga': 'Rp{:,}'.format(prediction).replace(',','.')})

@app.route('/predictharga', methods=['POST'])
def predictharga():
    cmpn = str.capitalize((request.form.get('company')))
    cpu = str(request.form.get('cpu'))
    ram = int(request.form.get('ram'))
    gpu = str(request.form.get('gpu'))
    opsys = str(request.form.get('opsys'))
    weight = float(request.form.get('weight'))
    hdd = int(request.form.get('hdd'))
    ssd = int(request.form.get('ssd'))

    kumpulan_data_queri = numpy.array([[cmpn,cpu,ram,gpu,opsys,weight,hdd,ssd]])
    harga      = int(numpy.exp(model.predict(kumpulan_data_queri)[0]))
    konversi_harga      = harga*16204
    prediction = int(konversi_harga)
    return render_template('hasil.html', prediction_text='Rp{:,}'.format(prediction).replace(',','.'))


if __name__ == "__main__":
    app.run(debug=True)
