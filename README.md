# Cineview4 -> Una app muy simple para mostrar algunas tecnologías ( y practicar ).

La app trae películas de theMovieDataBaseApi https://developers.themoviedb.org/, y permite que
el usuario vea cards con información muy simple sobre las mismas ( en la sección principal o Home).
También permite que el usuario las pueda guardar o eliminar de una sección de favoritos.

Es un trabajo en progreso, donde siempre se van agregando nuevas herramientas y features.

Esta app usa las siguientes tecnologías ->
    SplashScreen ( Splash API)
    ROOM ( persistencia de datos )
    Ktor ( API Rest )
    Compose UI ( para la presentación )
    Flows ( para el manejo asincrónico de datos desde los repositorios)
    Hilt ( inyección de dependencias )

Se optó por una arquitectura MVVM.
Para test se usó Junit4 ( trabajo en progreso)
    
* No se incluye páginado en este caso, ya que las librerías están aun en alfa para usarlas 
en compose ( de caso contrario, podría usar Paging3).
* Se incluirán en un futuro test instrumentados para Compose.
* No fue necesario agregar navegación ya que la app es muy simple, de tener una tercer vista ( 
detalle de la película, por ejemplo ) la navegación la realizaría en base a DataClasses
  /** Documentation
  * https://medium.com/androiddevelopers/viewmodel-one-off-event-
  * antipatterns-16a1da869b95
  * https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from
  * -android-uis-23080b1f8bda */
* 

