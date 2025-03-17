import 'package:flutter/material.dart';
import 'practica1.dart';
import 'practica2.dart';
import 'practica3.dart';
import 'practica4.dart';
import 'practica5.dart';
import 'practica6.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Prácticas Flutter',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MenuPrincipal(),
    );
  }
}

class MenuPrincipal extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Menú Principal'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica1()),
                );
              },
              child: Text('Práctica 1: Hola Mundo'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica2()),
                );
              },
              child: Text('Práctica 2: Contador de clicks'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica3()),
                );
              },
              child: Text('Práctica 3: Contador con temporizador y alert dialog'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica4()),
                );
              },
              child: Text('Práctica 4: Contador con historial en archivo de texto'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica5()),
                );
              },
              child: Text('Práctica 5: Almacenamiento en SQLite'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Practica6()),
                );
              },
              child: Text('Práctica 6: Contador con historial en SQLite'),
            ),
          ],
        ),
      ),
    );
  }
}