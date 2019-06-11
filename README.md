# Fake Shop

## About

This project is a technical experiment with new Android architecture 
guidelines including testing utilities. It's not opinionated about
how to further organize code beyond `ViewModel` other than basic
code patterns like repository or API abstractions.

In this test you won't find CLEAN / MVP / MVVMP / Flux / other
architectures implemented since they offer little new things.  

I had one objective while completing this technical test and it
was to update my set of ideas when developing new applications
beyond what is already common practice.

You can check those traditional architectures in other repositories I 
did in the past:

* [Flux sample app](https://github.com/pabloogc/Randomco)  
    A flux application that fetches users from an API 
    with a list and detail screen.
* [CLEAN (kinda) sample app](https://github.com/pabloogc/bequ)  
    A traditional (closer to no architecture) Application using 
    common Android libraries. This one is a bit outdated, take with care. 


## Libraries

Follow the ideas mentioned before, this application uses a 
not-so-popular set of libraries:

###### Data Modeling

All the application uses immutable data structures and mostly 
a functional style. This might make code slightly harder
to read at the expense of removing various families of bugs, 
mainly concurrency and inconsistencies between displayed information.

There is one single source of truth for mapping data to view and it's 
the view model state. Views are reactive to it and should
never modify it directly.

The question about _how to modify_ the view model is quite broad. 
You could either go CLEAN interactors, Flux Actions, plain methods, 
and so on. All serve same purpose, I decided to go plain methods
for the sake of simplicity.

No libraries are required but inline data classes are used for extra
type safety. They are experimental, and require compiler flag set from
gradle.

###### Dependency Injection: Kodein  

Used for dependency injection. Nothing inherently interesting about it
other than it's runtime based instead of compile time based like Dagger2.

Concepts from both libraries are easily translated between each other.

* Kodein  -> Component
* Kodein Module -> Dagger Module
* Binding -> Dagger `@Provides`
* Binding with `scope` (singleton, provider) -> Dagger scopes

Application is organized to be single activity, so one module is 
scoped to application (`AppModule`) which hold activity independent 
dependencies, and another scoped to Activity (`MainModule`) which 
holds view models and other view related dependencies.

###### Reactive: Coroutines + ViewModel + LiveData

Used to build reactive UI. There was no real need to include RX 
in this small application, you can find heavy RX usage in Flux sample.

All three components integrate pretty well among each other and since
ViewModels are scoped we get most RX bookkeeping done automatically
by launching coroutines in the `viewModelScope` scope.

On top of that, `LiveData` is an `Observable` for all intents and 
purposes (and in fact can be transformed into one)
unless you want advanced operators.

Integration with dependency injection is a bit tricky since our 
`ViewModel` have external dependencies passed in constructor,
solved with a `ViewModelFactory`. Check details in `KodeinUtil.kt`

###### Network: Retrofit + Moshi

Plain retrofit with coroutines integration. Latest published Retrofit
version does not support coroutines by default so it's pointing
to 2.5.1 snapshot.

Json parsing is doing with Moshi since it can correctly map
Kotlin properties. Other json libraries like Gson map using the java field 
so kotlin nullability checks are lost leading to really weird bugs.

###### Testing: JUnit + Robolectric + Mockito + Strikt + Espress

Usual testing libs, with Strikt for fluent assertions. 
There are many options for assertion libs, or testing frameworks like
`Spek`, however including it seemed overkill despite it's
nice features.     

Testing UI with espresso tests can be a bit challenging since
we have to provide ViewModels with fake dependencies. 
Check example at `CartFragmentTest`. Android fragment testing library
removes a lot of boilerplate for isolating a Fragment.


## Final note

For this test I tried to include a small piece of technology of every
kind but not aim for high coverage for testing, or showcase all possible
scenarios / techs.

Some small user oriented features were left out, like error handling
other than a basic retry button or displaying more information
about products what discounts have been applied. Those were not the 
focus of this test but the app is made so that they could be easily 
added.

Some simplifications / assumptions has been made like hardcoding
active discounts (under an abstraction layer) or how discounts work
in general.





