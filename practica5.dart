import 'package:flutter/material.dart';
import 'database_helper.dart';




class Practica5 extends StatefulWidget {
  @override
  _Practica5State createState() => _Practica5State();
}

class _Practica5State extends State<Practica5> {
  final DatabaseHelper _dbHelper = DatabaseHelper();
  final TextEditingController _controller = TextEditingController();
  List<Map<String, dynamic>> _items = [];

  @override
  void initState() {
    super.initState();
    _refreshItems();
  }

  void _refreshItems() async {
    final data = await _dbHelper.getItems();
    setState(() {
      _items = data;
    });
  }

  void _addItem() async {
    if (_controller.text.isEmpty) return;
    await _dbHelper.insertItem({'name': _controller.text});
    _controller.clear();
    _refreshItems();
  }

  void _updateItem(int id) async {
    if (_controller.text.isEmpty) return;
    await _dbHelper.updateItem({'id': id, 'name': _controller.text});
    _controller.clear();
    _refreshItems();
  }

  void _deleteItem(int id) async {
    await _dbHelper.deleteItem(id);
    _refreshItems();
  }

  void _showInputDialog({int? id, String? currentName}) {
    _controller.text = currentName ?? '';
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(id == null ? 'Add Item' : 'Edit Item'),
        content: TextField(
          controller: _controller,
          decoration: InputDecoration(hintText: 'Enter item name'),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              if (id == null) {
                _addItem();
              } else {
                _updateItem(id);
              }
              Navigator.pop(context);
            },
            child: Text('Save'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('practica5: Almacenamiento sqlite'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: _items.isEmpty
            ? Center(child: Text('No items added yet!'))
            : ListView.builder(
                itemCount: _items.length,
                itemBuilder: (context, index) {
                  final item = _items[index];
                  return Card(
                    elevation: 3,
                    margin: EdgeInsets.symmetric(vertical: 8, horizontal: 4),
                    child: ListTile(
                      title: Text(item['name']),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          IconButton(
                            icon: Icon(Icons.edit, color: Colors.blueAccent),
                            onPressed: () => _showInputDialog(id: item['id'], currentName: item['name']),
                          ),
                          IconButton(
                            icon: Icon(Icons.delete, color: Colors.redAccent),
                            onPressed: () => _deleteItem(item['id']),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showInputDialog(),
        child: Icon(Icons.add),
      ),
    );
  }
}