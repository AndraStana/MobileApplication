import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    AsyncStorage,
    Button,
    TextInput}
from'react-native';
import React, {Component} from 'react'
import {Movie} from './Movie'


export class AddMovie extends Component{


    static navigationOptions={
        header:null,
}
    constructor(props){
        super(props);
        this.state={
            newTitle:"",
            newProducer:"",
            newYear:"",
            newStoryline:"",
            newGenre:""
        }
    }


    async save(){
        let response = await AsyncStorage.getItem('@MovieStore:key');
        let movies = JSON.parse(response);
        if(movies.length==0){
            movies.push({key:0, movie: new Movie(this.state.newTitle,this.state.newProducer, this.state.newYear,this.state.newGenre, this.state.newStoryline)});
        }
        else
        {
            var length = movies.length;
            movies.push({key:movies[length-1].key+1, movie: new Movie(this.state.newTitle,this.state.newProducer, this.state.newYear,this.state.newGenre, this.state.newStoryline)});
        }

        AsyncStorage.setItem('@MovieStore:key', JSON.stringify(movies));
    }

    render(){
        const {params} = this.props.navigation.state;
        const {goBack} = this.props.navigation;

        return(
        <View style={styles.container}>
            <ScrollView>
                <Text style={styles.title}> ADD MOVIE: </Text>
                <Text>Title</Text>
                <TextInput placeholder="Title" onChangeText={(text)=>this.setState({newTitle:text})}> </TextInput>
                <Text>Producer</Text>
                <TextInput placeholder="Producer" onChangeText={(text)=>this.setState({newProducer:text})}> </TextInput>
                <Text>Year</Text>
                <TextInput placeholder="Year" onChangeText={(text)=>this.setState({newYear:text})}> </TextInput>
                <Text>Genre</Text>
                <TextInput placeholder="Genre" onChangeText={(text)=>this.setState({newGenre:text})}> </TextInput>
                <Text>Storyline</Text>
                <TextInput placeholder="Storyline" onChangeText={(text)=>this.setState({newStoryline:text})}> </TextInput>
            </ScrollView>


            <View>
                <Button color={"#1EA713"} onPress={
                    async () => {
                        await this.save();
                        params.refresh();
                        goBack();
                    }
                }
                title="SAVE" />
            </View>
        </View>
        );
    }
}

const styles = StyleSheet.create({

    title:{
        fontSize: 30,
        alignSelf: 'center',
    },

    container: {
        flex: 1,
        justifyContent: 'center',

        backgroundColor: '#76F1DC',

    },


    singleItemDetails:{
        marginTop:20
    },

});
