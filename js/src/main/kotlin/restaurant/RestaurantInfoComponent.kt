package restaurant

import kotlinx.css.*
import kotlinx.html.title
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*
import styled.css
import styled.styledDiv
import styled.styledImg

class RestaurantInfoComponent(props: Props) : RComponent<RestaurantInfoComponent.Props, RState>(props) {

    override fun RBuilder.render() {
        val restaurant = props.restaurant
        div {
           for (image in restaurant.photos) {
               styledImg(src = image.url()) {
                   css {
                       borderRadius = LinearDimension("10px")
                       width = LinearDimension("150px")
                       height = LinearDimension("150px")
                       marginRight = LinearDimension("10px")
                   }
               }
           }
           styledDiv {
               h2 {
                   +restaurant.name
               }
               div {
                   span {
                       +"Описание ресторана: "
                   }
                   span {
                       +restaurant.description.toString()
                   }
               }
               div {
                   span {
                       +"Сайт: "
                   }
                   span {
                       +restaurant.websiteUrl.toString()
                   }
               }
               div {
                   span {
                       +"Телефон: "
                   }
                   span {
                       +restaurant.phone.toString()
                   }
               }
               div {
                   span {
                       +"⭐ "
                   }
                   span {
                       attrs {
                           title = "Внутренний рейтинг"
                       }
                       +restaurant.internalRating.toString()
                   }
                   span {
                       +" / "
                   }
                   span {
                       attrs {
                           title = "Внешний рейтинг"
                       }
                       +restaurant.externalRating.toString()
                   }
               }
           }
        }
    }

    interface Props: RProps {
        var restaurant: RestaurantInfo
    }
}