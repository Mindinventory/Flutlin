// Copyright 2015 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyHome());
}

class MyHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutlin',
      theme: ThemeData(
        primarySwatch: Colors.grey,
      ),
      home: MyUsers(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyUsers extends StatefulWidget {
  @override
  _MyUsersState createState() => _MyUsersState();
}

class _MyUsersState extends State<MyUsers> {
  static const String _channel = 'api';
  static const String _emptyMessage = '';
  static const BasicMessageChannel<String> platform =
  BasicMessageChannel<String>(_channel, StringCodec());
  bool apiCallCompleted = false;
  List<String> images = List();

  @override
  void initState() {
    super.initState();
    platform.setMessageHandler(_handlePlatformMessage);
  }

  /// this function helps to manage the message received under MessageChannel.
  Future<String> _handlePlatformMessage(String message) async {
    final List<dynamic> imageDynamicList = json.decode(message);
    // ignore: always_specify_types
    for (var post in imageDynamicList) {
      images.add(post.toString());
    }
    setState(() {
      apiCallCompleted = true;
    });
    return _emptyMessage;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Container(
            color: Colors.grey[100],
          ),
          _getScreenBody(context)
        ],
      ),
    );
  }

  /// this widget helps to create UI for body part.
  Widget _getScreenBody(BuildContext context) {
    if (apiCallCompleted) {
      return GridView.builder(
          gridDelegate:
          SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
          itemBuilder: _getRowWidget,
          itemCount: images.length);
    } else {
      return Center(child: Text('Calling API...'));
    }
  }

  /// this widget is used as row tile for GridView.
  Widget _getRowWidget(BuildContext context, int index) {
    return Padding(
      padding: const EdgeInsets.all(0.1),
      child: Container(
          width: double.infinity,
          height: 190.0,
          decoration: BoxDecoration(
            /*shape: BoxShape.circle,*/
              border: Border.all(color: Colors.grey[350], width: 1.0),
              image: DecorationImage(
                  fit: BoxFit.cover, image: NetworkImage(images[index])))),
    );
  }
}
