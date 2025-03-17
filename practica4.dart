import 'dart:io';
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';



class Practica4 extends StatefulWidget {
  @override
  _Practica4State createState() => _Practica4State();
}

class _Practica4State extends State<Practica4> {
  int _score = 0;
  bool _isPlaying = false;
  late DateTime startTime;
  int _timeLeft = 5;
  Timer? _timer;
  
  void _startGame() {
    setState(() {
      _score = 0;
      _isPlaying = true;
      startTime = DateTime.now();
      _timeLeft = 5;
    });

    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        if (_timeLeft > 0) {
          _timeLeft--;
        } else {
          _isPlaying = false;
          _timer?.cancel();
          _saveScore(_score);
        }
      });
    });
  }

      @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _incrementScore() {
    if (_isPlaying) {
      setState(() {
        _score++;
      });
    }
  }

  Future<File> get _file async {
    final directory = await getApplicationSupportDirectory();
    return File('${directory.path}/scores.txt');
  }

  Future<void> _saveScore(int score) async {
    final file = await _file;
    final timestamp = DateTime.now().toIso8601String();
    await file.writeAsString('$timestamp: $score\n', mode: FileMode.append);
  }

  Future<List<String>> _loadScores() async {
    try {
      final file = await _file;
      return await file.readAsLines();
    } catch (e) {
      return [];
    }
  }

  void _showScores() async {
    List<String> scores = await _loadScores();
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text("Historial de Puntuaciones"),
          content: SingleChildScrollView(
            child: ListBody(
              children: scores.map((s) => Text(s)).toList(),
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: Text("Cerrar"),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Practica4: historial txt")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Puntuación: $_score", style: TextStyle(fontSize: 24)),
            SizedBox(height: 20),
            Text("Tiempo restante: $_timeLeft s", style: TextStyle(fontSize: 20, color: Colors.red)),
            SizedBox(height: 20),
            _isPlaying
                ? ElevatedButton(
                    onPressed: _incrementScore,
                    child: Text("Tocar"),
                  )
                : ElevatedButton(
                    onPressed: _startGame,
                    child: Text("Iniciar"),
                  ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _showScores,
              child: Text("Ver Historial"),
            ),
          ],
        ),
      ),
    );
  }
}
