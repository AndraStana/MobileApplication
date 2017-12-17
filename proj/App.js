/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    Platform,
    StyleSheet,
    Text,
    View, FlatList, ScrollView, TextInput, Button

} from 'react-native';
import {StackNavigator} from 'react-navigation'
import { Details} from './Details'
import {EmailComponent} from './EmailComponent'
import {MovieList} from './MovieList'
import {AddMovie} from './AddMovie'
import {ReviewMovie} from './ReviewMovie'




export default class App extends Component<{}> {
  render() {
      return <MyApplication/>
  }
}



const MyApplication = StackNavigator({
    Home: {screen: MovieList},
    Email: {screen: EmailComponent},
    Details: {screen: Details},
    AddMovie: {screen: AddMovie},
    ReviewMovie : {screen: ReviewMovie}
})

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#D09BCF',
  },


    singleItemDetails:{
        marginTop:20
    },

    item: {

            padding: 10,
            fontSize: 18,
            height: 44,
        },

    linearView: {
            flexDirection:'row',
            padding:10,
        },
   movieList:{
    marginTop:50,
       marginBottom: 50,
       backgroundColor: '#F91111',

   },

  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
