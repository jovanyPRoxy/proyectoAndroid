import 'dart:async';
import 'package:flutter/material.dart';





class Practica3 extends StatefulWidget {

  final String title='Practica3:Contador con temporizador';

  @override
  State<Practica3> createState() => _Practica3State();
}

class _Practica3State extends State<Practica3> {
  int _counter = 0;
  int _timeRemaining = 5;
  bool _gameStarted = false;
  Timer? _timer;

  void _startGame() {
    setState(() {
      _counter = 0;
      _timeRemaining = 5;
      _gameStarted = true;
    });
    _startTimer();
  }

  void _incrementCounter() {
    if (_gameStarted) {
      setState(() {
        _counter++;
      });
    }
  }

  void _startTimer() {
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      setState(() {
        if (_timeRemaining > 0) {
          _timeRemaining--;
        } else {
          _gameStarted = false;
          _timer?.cancel();
        }
      });
    });
  }

    @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Tiempo restante: $_timeRemaining segundos',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 20),
            Text(
              'Contador: $_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _gameStarted ? null : _startGame,
              child: const Text('Iniciar'),
            ),
            const SizedBox(height: 10),
            ElevatedButton(
              onPressed: _gameStarted ? _incrementCounter : null,
              child: const Text('Presionar'),
            ),
          ],
        ),
      ),
    );
  }
}