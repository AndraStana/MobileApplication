import {
    StyleSheet,
    Text,
    View,
    AsyncStorage,
    Button,
    Picker}
    from'react-native';
import React, {Component} from 'react'
import {Review} from './Review'


export class ReviewMovie extends Component{


    static navigationOptions={
        header:null,
    }
    constructor(props){
        super(props);
        this.state={
            newValue:"",
        }
    }

    async findMovieByTitle(title){
        let response = await AsyncStorage.getItem('@MovieStore:key');
        let movies = JSON.parse(response);
        var length = movies.length;
        for(var i=0;i<length;i++){
            if(movies[i].movie.title===title){
                return i ;
            }
        }
        return -1;
    }

    async save(movieId){
        let response = await AsyncStorage.getItem('@MovieReviews:key');
        let reviews = JSON.parse(response);
        if(reviews == null){
            reviews = [];
            reviews.push({key:0, review: new Review(movieId,this.state.newValue)});
        }
        else
        {
            var length = reviews.length;
            reviews.push({key:reviews[length-1].key+1, review: new Review(movieId,this.state.newValue)});
        }
        AsyncStorage.setItem('@MovieReviews:key', JSON.stringify(reviews));
    }

    render(){
        const {params} = this.props.navigation.state;
        const {goBack} = this.props.navigation;

        return(
            <View style={styles.container}>
                <Text style={styles.title}> Review MOVIE: </Text>
                <Picker
                 selectedValue={this.state.newValue}
                 onValueChange={(newValue) =>{ this.setState({newValue})}}>
                    <Picker.Item label="Very Bad" value="0" />
                    <Picker.Item label="Bad" value="1" />
                    <Picker.Item label="Meh" value="2" />
                    <Picker.Item label="Good" value="3" />
                    <Picker.Item label="Very good" value="4" />
                </Picker>

                <View>
                    <Button color={"#1EA713"} onPress={
                        async () => {
                            var movieId = await this.findMovieByTitle(params.movie.title);
                            await this.save(movieId);
                            goBack();
                        }
                    }
                            title="ADD" />
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
