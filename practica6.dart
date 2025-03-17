import 'dart:async';
import 'package:flutter/material.dart';
import 'database_helperscore.dart';
import 'score.dart';



class Practica6 extends StatefulWidget {
  @override
  _Practica6State createState() => _Practica6State();
}

class _Practica6State extends State<Practica6> {
  int _puntos = 0;
  bool _juegoEnCurso = false;
  Timer? _timer;
  int _tiempoRestante = 10;
  TextEditingController _nombreController = TextEditingController();
  List<Score> _scores = [];

  @override
  void initState() {
    super.initState();
    _cargarScores();
  }

  void _cargarScores() async {
    _scores = await DatabaseHelper.instance.getScores();
    setState(() {});
  }

  void _iniciarJuego() {
    setState(() {
      _puntos = 0;
      _juegoEnCurso = true;
      _tiempoRestante = 5;
    });

    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _tiempoRestante--;
        if (_tiempoRestante == 0) {
          _terminarJuego();
        }
      });
    });
  }

  void _terminarJuego() {
    _timer?.cancel();
    setState(() {
      _juegoEnCurso = false;
    });

    _mostrarDialogoGuardarScore();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }


  void _mostrarDialogoGuardarScore() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          backgroundColor: Colors.grey[850],
          title: Text('Guardar puntaje', style: TextStyle(color: Colors.white)),
          content: TextField(
            controller: _nombreController,
            decoration: InputDecoration(
              labelText: 'Nombre',
              labelStyle: TextStyle(color: Colors.white),
              enabledBorder: UnderlineInputBorder(
                borderSide: BorderSide(color: Colors.blue),
              ),
            ),
            style: TextStyle(color: Colors.white),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                _guardarScore();
              },
              child: Text('Guardar', style: TextStyle(color: Colors.blue)),
            ),
          ],
        );
      },
    );
  }

  void _guardarScore() async {
    final score = Score(nombre: _nombreController.text, puntos: _puntos);
    await DatabaseHelper.instance.insertScore(score);
    _nombreController.clear();
    _cargarScores();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Minijuego')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Puntos: $_puntos', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: const Color.fromARGB(255, 5, 1, 1))),
            SizedBox(height: 10),
            Text('Tiempo: $_tiempoRestante', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.red)),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _juegoEnCurso ? () => setState(() => _puntos++) : _iniciarJuego,
              style: ElevatedButton.styleFrom(
                padding: EdgeInsets.symmetric(vertical: 15, horizontal: 40),
                textStyle: TextStyle(fontSize: 20),
                backgroundColor: _juegoEnCurso ? Colors.green : const Color.fromARGB(255, 0, 0, 0),
              ),
              child: Text(_juegoEnCurso ? '¡Presiona!' : '¡Iniciar juego!'),
            ),
            SizedBox(height: 30),
            Text('Puntajes:', style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold, color: const Color.fromARGB(255, 0, 0, 0))),
            Expanded(
              child: ListView.builder(
                itemCount: _scores.length,
                itemBuilder: (context, index) {
                  return Card(
                    color: Colors.grey[800],
                    child: ListTile(
                      title: Text(_scores[index].nombre, style: TextStyle(color: Colors.white)),
                      trailing: Text('${_scores[index].puntos}', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.blue)),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
