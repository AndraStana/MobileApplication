import {
    FlatList,
    StyleSheet,
        Text,
        View,
        ScrollView,
        Button,
        RefreshControl,
        AsyncStorage,
        Alert
} from 'react-native'

import React,{Component} from 'react'


export class MovieList extends Component{

    static navigationOptions = {
                //title: 'Home',
                header:null,
    };


    constructor(props){
        super(props);
        this._onRefresh=this._onRefresh.bind(this);
        this.state={
            refreshing:false,
            movies:[],
            loading:true,
        }
    }


    _onRefresh(){
        this.setState({refreshing:true});
        this.setState({refreshing:false});
        this.retrieveContent();
    }

     retrieveContent(){
        AsyncStorage.getItem('@MovieStore:key').then((value)=>
        {

            this.setState({movies: JSON.parse(value)});

        }).catch((error)=>{
            console.log("Cannot retrieve content. Error: " + error)
        })
    }

    componentWillMount(){
        this.getItems();
    }



    getItems(){
        AsyncStorage.getItem('@MovieStore:key').then((value) =>{
           this.setState({movies: JSON.parse(value)});
            this.setState({loading:false});

        }).catch((error)=>{
            console.log("Cannot retrieve content. Error: " + error);
        });
    }


    async deleteMovie(id){
        let response = await AsyncStorage.getItem('@MovieStore:key');
        let movies = JSON.parse(response);
        movies.splice(id,1);
        AsyncStorage.setItem('@MovieStore:key',JSON.stringify(movies));
    }

    deleteWithConfirmation(title){
        Alert.alert('WARNING', 'Are you sure?',

            [
                {text: 'YES',
                    onPress:async()=>{
                        var id = await this.findByTitle(title);
                        await this.deleteMovie(id);
                        this._onRefresh();
                    }
                },
                {
                    text:'NO'
                }
            ],
            {cancelable:false}

            )
    }

    async findByTitle(title){
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

    render(){
        const {navigate} = this.props.navigation;
        console.log(this.state.movies.length);
        if(this.state.loading !== true) {
            return (
                <View style={styles.container}>
                    <Text style={styles.title}> Movies </Text>
                    <FlatList
                        refreshControl={
                            <RefreshControl
                                refreshing={this.state.refreshing}
                                onRefresh={this._onRefresh.bind(this)}
                            />
                        }
                        data={this.state.movies}
                        //data = { movies }
                        renderItem={
                            ({item}) =>
                                <ScrollView>

                                    <View style={styles.linearView}>
                                        <Button color = "#77797B" onPress={() => navigate('ReviewMovie',{movie:item.movie})}
                                                title="Review" >
                                        </Button>
                                        <Text style={styles.item} onPress={() => navigate('Details', {
                                            movie: item.movie,
                                            refresh: this._onRefresh
                                        })
                                        }>{item.movie.title} -> {item.movie.producer}</Text>


                                        <View style={styles.buttonDelete}>
                                            <Button color='#F91111' onPress={() =>{
                                                this.deleteWithConfirmation(item.movie.title)
                                            }}
                                                    title="X" >
                                            </Button>

                                        </View>


                                    </View>
                                </ScrollView>
                        }

                        extraData={this.state.movies}
                    />

                    <View >
                        <Button onPress={() =>navigate('Email')}
                                title="Send Email" >
                        </Button>
                    </View>
                    <View >
                        <Button  color = "#77797B" onPress={() =>navigate('AddMovie', { refresh: this._onRefresh})}
                                title="Add Movie" >
                        </Button>
                    </View>
                </View>
            )
        }
        else
        {
            return(
               <View>
                   <Text> LOADING :D </Text>
               </View>
            )
        }
    }

}

const styles = StyleSheet.create({

    title:{
        fontSize: 40,
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

    buttonDelete:{
        alignSelf: 'flex-end',
        alignItems: 'flex-end',
        flex:1,
        //marginRight:60,

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
