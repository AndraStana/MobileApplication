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
    View, FlatList, ScrollView, TextInput, Button,Linking

} from 'react-native';
import {StackNavigator} from 'react-navigation'


var movies=[
    {
      key:'1',
        title:'movie1',
        producer:'producer1',
        year:'1990',
        genre:'genre1',
        storyline:'storyline1'
    },
    {
        key:'2',
        title:'movie2',
        producer:'producer2',
        year:'2000',
        genre:'genre2',
        storyline:'storyline2'
    },
    {
        key:'3',
        title:'movie3',
        producer:'producer3',
        year:'2010',
        genre:'genre3',
        storyline:'storyline3'
    }
]



export default class App extends Component<{}> {
  render() {
      return <MyApplication/>
  }
}


class MovieList extends Component{

  render(){
      const {navigate} = this.props.navigation;
    return(
        <View style={styles.container}>
          <Text style={{ fontSize:30}}> Movies </Text>
          <FlatList
                data = { movies }
                renderItem={
                      ({item}) =>
                          <ScrollView>
                              <View style={styles.linearView} >
                                <Text style={styles.item} onPress={() => navigate('Details',{ movie : item })
                                                                        } >{item.title} -> {item.producer}</Text>
                              </View>
                          </ScrollView>
                      }
             />

          <View >
            <Button onPress={() =>navigate('Email')}
                    title="Send Email" >
           </Button>
           </View>
        </View>
    )
  }

}


class Details extends Component{

    render(){
        const {state} = this.props.navigation;
        var movie = state.params ? state.params.movie : "<undefined>";
        return(
        <View style={styles.container}>

            <View  style={styles.singleItemDetails}>
              <Text>{movie.title}</Text>
              <TextInput> {movie.producer} </TextInput>
              <TextInput> {movie.year} </TextInput>
              <TextInput> {movie.genre} </TextInput>

              <ScrollView>
                     <TextInput style={{height: 100, width: 300, marginTop:10 }} multiline={true}> {movie.storyline} </TextInput>
              </ScrollView>
            </View>
        </View>

        );
    }
}


class EmailComponent extends Component{

    render(){
      return(

        <View>
          <TextInput onChangeText={(email)=>this.setState({email})} />
          <TextInput onChangeText={(content)=>this.setState({content}) }/>
          <Button
              onPress={() => {
                                                subject = "Email sent from React Native";
                                                all = "mailto:" + this.state.email + "?subject=" + subject + "&body=" + this.state.content ;
                                                Linking.openURL(all)}}
              title="Send Email"
              color="#841584"
          />
        </View>


      )
    }
}

const MyApplication = StackNavigator({
    Home: {screen: MovieList},
    Email: {screen: EmailComponent},
    Details: {screen: Details}
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
