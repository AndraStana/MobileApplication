import React, { Component} from 'react';
import{
    StyleSheet,
    Text,
    View,
    ScrollView,

    TextInput,
    Button,
    AsyncStorage
} from 'react-native'



export class Details extends Component{
    static navigationOption = {
        header:null,
    }


    constructor(props){
        super(props);
        this.state={
            newProducer:"",
            newYear:"",
            newStoryline:"",
            newGenre:""
        }
    }

    async updateMovie(index,producer,year,storyline,genre){
        let response=  await AsyncStorage.getItem('@MovieStore:key');
        let movies = JSON.parse(response);
        if(producer!==""){
            movies[index].movie.producer=producer;
        }
        if(year!==""){
            movies[index].movie.year=year;
        }
        if(storyline!==""){
            movies[index].movie.storyline=storyline;
        }
        if(genre!==""){
            movies[index].movie.genre=genre;
        }
        AsyncStorage.setItem('@MovieStore:key', JSON.stringify(movies));
    }

    async getMovieIdByTitle(title){
        let response =  await AsyncStorage.getItem('@MovieStore:key');
        let movies=JSON.parse(response);
        var length = movies.length;
        for(var i=0;i<length;i++){
            if(movies[i].movie.title===title){
                return i ;
            }
        }
        return -1;
    }



    render() {
        const {params} = this.props.navigation.state;
        const {goBack} = this.props.navigation;
        //const {state} = this.props.navigation;
        var movie = params ? params.movie : "<undefined>";
        //this.setState({newProducer:params.movie.producer});
        return (
            <View style={styles.container}>

                <View style={styles.singleItemDetails}>
                    <Text>{movie.title}</Text>
                    <TextInput onChangeText={(text)=> this.setState({newProducer:text})}> {movie.producer}</TextInput>
                    <TextInput  onChangeText={(text)=> this.setState({newYear:text})}> {movie.year}</TextInput>
                    <TextInput  onChangeText={(text)=> this.setState({newGenre:text})}> {movie.genre}</TextInput>

                    <ScrollView>
                        <TextInput style={{height: 100, width: 300, marginTop: 10}}
                                   multiline={true}
                                   onChangeText={(text)=> this.setState({newStoryline:text})}> {movie.storyline}
                            </TextInput>
                    </ScrollView>

                    <View>
                        <Button onPress={
                            async () => {
                                var id = await this.getMovieIdByTitle(params.movie.title);
                                await this.updateMovie(id, this.state.newProducer,this.state.newYear,this.state.newStoryline,this.state.newGenre);
                                params.refresh();
                                goBack();
                            }
                        }
                            title="SAVE" />
                    </View>
                </View>
            </View>

        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#76F1DC',

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
