---
title: Laboratorio de Programación Orientada a Objetos
author: Nielsen Nicolas, Julian Piva
---

El enunciado del laboratorio se encuentra en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

# 1. Tareas
Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.
- [x] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del programa.

## 1.1. Interfaz de usuario
- [x] Estructurar opciones
- [x] Construir el objeto de clase `Config`

## 1.2. FeedParser
- [x] `class Article`
    - [x] Atributos
    - [x] Constructor
    - [x] Método `print`
    - [x] _Accessors_
- [x] `parseXML`

## 1.3. Entidades nombradas
- [x] Pensar estructura y validarla con el docente
- [x] Implementarla
- [x] Extracción
    - [x] Implementación de heurísticas
- [x] Clasificación
    - [x] Por tópicos
    - [x] Por categorías
- Estadísticas
    - [x] Por tópicos
    - [x] Por categorías
    - [x] Impresión de estadísticas

## 1.4 Limpieza de código
- [x] Pasar un formateador de código
- [x] Revisar TODOs

# 2. Experiencia

    El concepto del lab 2 nos parecio entretenido de llevar a cabo y nos llevo a utilizar mucho de lo que caracteriza a Java y la POO.

    Esta experiencia de laboratorio fue muy enriquecedora, cracias a todo el conocimiento previo de lenguajes puramente 
    imperativos/procedurales, de nivel relativamente bajo, como C y lenguajes "casi" puramente funcionales como Haskell,
    la abstraccion de orientacion a objetos se siente como algo organico y facil de razonar.
    La encapsulacion de conceptos y funcionalidades relacionadas en una sola clase que las englobe creo personalmente que hace que el codigo sea mas entendible a primera vista, por lo menos en lo que respecta al "que hace", no tanto asi al "como lo hace".
    Seguir la cadena de abstracciones y herencia aveces se hace tedioso y solo se llega a entender el concepto de un metodo o clase por su nombre o nomenclatura, lo cual no creo que sea conveniente cuando se quiere saber especificamente que esta haciendo el programa a un nivel muy detallado.

    A favor de Java :
        Facil de leer (por lo menos para nosotros), facil de aprender.
        Lenguaje completo (al menos al dia de hoy), las librerias estandar de Java tienen casi todas las funcionalades que se pueden querer en un lenguaje, como por ejemplo y algunas de las favoritas heredadas del paradigma funcional : Reduce, Map, Filter, Funciones lambda. Solo esas 4 utilidades por si solas son geniales.

    En contra : 
        Algo bastante habitual es decir que jave es "too verbose" y es verdad a veces hay que escribir demasiado para hacer algo que no es tan complejo, el ejemplo habitual del hello world en java.
        No es agradable que todo tenga que ser una clase, a veces solo quiero una funcion simple, pero dejar una funcion generica en una clase que no tiene relacion directa con dicha funcion rompe un poco con el encapsulamiento y hace que sea poco claro su proposito,
        por lo tanto quedas obligado a crear archivos y clases que tienen como proposito una sola funcion simple.

# 3. Preguntas
1. Explicar brevemente la estructura de datos elegida para las entidades nombradas.
2. Explicar brevemente cómo se implementaron las heurísticas de extracción.

# Respuestas
1. Clase NamedEntity :
    Campos:
        Label :
            Es la etiqueta por la cual se refiere a la entidad nombrada aunque esta se presente con un formato distinto
            ej.: Label = Trump, Formatos posibles = Donald Trump, Donald John. Trump, Trump
        Topics :
            Lista de topicos con los cuales se relaciona la entidad nombrada, representados por la clase Topic
            ej.: Label = Trump, Topics = ["POLITICS", "BUSINESS"]
        Category :
            Categoria en la cual se encasilla a la entidad nombrada, representada por la clase Category
            ej.: Label = Trump, Category = "PERSON"
            ej.: Label = Cordoba, Category = "Location"
        Keywords :
            Lista de palabras o conjunto de palabras por las cuales se reconoce a una entidad, los formatos antes mencionados en
            el campo Label
            ej.: Label = Trump, Keywords = ["Trump", "Donald Trump", "Donald John Trump"]

    Metodos:
        getLabel() : Devuelve el String label de esa instancia de NamedEntity
        getTopics() : Devuelve la lista de Topic, topics, de esa instancia de NamedEntity
        getCategory() : Devuelve la Category, category, de esa intancia de Namedentity
        getKeywords() : Devuelve la lista de String, keywords, de esa instancia de NamedEntity
        print() : Printea por System.out todos los campos correspondientes a esa instancia de Namedentity

    Constructor:
        El constructor de la clase NamedEntity no es de acceso publico, la unica manera de construir una instancia de la clase
        es mediante la clase NamedEntityBuilder, usando el patron builder tenemos la posibilidad de "armar" las partes de la instancia de NamedEntity en momentos distintos y no necesariamente en simultaneo, tambien nos da la posibilidad de especificar solo algunos campos, los que queramos con los metodos NamedentityBuilder.withXXX(), y optar por dejar uno o todos con los valores por defecto que utiliza la clase.

        Campos NamedEntityBuilder :
            Idem NamedEntity

        Metodos NamedEntityBuilder :
            Constructor NamedEntityBuilder :
                Crea una instancia de la clase NamedEntityBuilder con los valores por defecto :
                label = "EMPTY";
                topics = new ArrayList<>();
                category = new Category("OTHER");
                keywords = new ArrayList<>();
        
            TODOS LOS CAMPOS DE NamedEntityBuilder SON ESTATICOS, es decir, son variables de clase, por lo tanto si
            existen mas de una instancia de NamedEntityBuilder todas se refieren a los mismos campos, y todas construyen instancias identicas.

            withLabel(String l) : 
                Asigna l al campo label
            withTopics(List<Topic> t) :
                Asigna t al campo topics
            withCategory(Category c) :
                Asigna c al campo category
            withKeywords(List<String> k) :
                Asigna k al campo keywords
            build() :
                Retorna una nueva instancia de NamedEntity llamando a su constructor con los valores que esten en los campos correspondientes

        

2. Las heuristicas fueron implementadas cada una con su propia clase la cual heredaba una interfaz con dos metodos, getname() para colocarle un nombre apropiado a la heuristica y extractcandidates() la cual toma como parametro un 'String text'. Dentro de la implementacion de extractcandidates() se modifico el texto pasado como parametro para removerle algunos caracteres y normalizarlos. Luego mediante una libreria llamada regex utilizamos dos metodos llamados pattern y matcher. Pattern usado para generar un patron de palabras mediante expresiones regulares las cuales seran comparadas mediante el metodo Matcher con el texto pasado como parametro. La expresion regular que toma pattern genera cualquier palabra de la A a la Z exepto en el caso de la Heuristica "Only Messi" ya que genera la expresion regular correspondiente a Messi y luego la compara con el texto para ver si ocurre en este. Una vez generadas las expresiones regulares, se utiliza el metodo matcher() que toma como parametro el texto modificado previamente. Luego el metodo matcher.find() nos indicara si existe una coincidencia entre la expresion regular y alguna palabra del texto dado. En algunos casos dentro de este ciclo que contiene matcher.find() se utilizan ciclos for o condicionales if para los diferentes propositos de las Heuristicas. Una vez que se encuentra una coincidencia entre la expresion regular y el texto y se cumplan las condiciones necesarias, se agrega la palabra (en el caso de esta libreria cuando ocurre un matcher.find() este genera un string matcher.group()) a una 'List<String>' llamada candidates y esta lista es la devolucion del metodo.

# 4. Extras
Completar si hacen algo.