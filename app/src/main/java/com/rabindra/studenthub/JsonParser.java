package com.rabindra.studenthub;
public class JsonParser {

    public static String parseData(String query)
    {
        String answer="Please provide more information like Student name or registration no for student details.\nFaculty full name for faculty details.";
        String[] words = query.split("\\s+");
        for (String word : words) {
            //Pre defined questions
            if (word.equals("hi") || word.equals("hii") || word.equals("hlo") || word.equals("hello") || word.equals("hiii") || word.equals("greetings")) {
                answer = "Hi, How may I help you?\nWhat are you looking for?";
                return answer;
            } else if (query.contains("what is your name")) {
                answer = "My name is MCA Student Hub\nI am here to help";
                return answer;
            } else if (query.contains("what is your college name") || query.contains("college name") || query.contains("college")) {
                answer = "College name is:\nIndira Gandhi Institute of Technology(IGIT)";
                return answer;
            } else if (word.contains("hod") || word.contains("hod of") || query.contains("head of department")) {
                answer = "HOD of MCA department is:\nProf. Dr. Sasmita Mishra";
                return answer;
            }  else if (word.contains("2023") && word.contains("batch"))
            {
                answer="MCA 2023 batch is: 40th batch of MCA";
            }
            else if(word.contains("time") || query.contains("time table"))
            {
                answer="https://mcastudenthub.blogspot.com/p/time-table-for-mca-provided-by-student.html";
                return answer;
            }
            else if(word.contains("syllabus") || query.contains("syllabus") || query.contains("course schedule") || query.contains("schedule"))
            {
                answer="https://drive.google.com/file/d/1wBDEbq9UttgVfQP0CJieAJxOL73vW68_/view?usp=sharing";
                return answer;
            }

            //Specific data used for the data moddel
            else if (query.contains("abhisek") && query.contains("satapathy") || word.equals("2105105001"))
            {
                answer="Name: Abhisek Satapathy\nSem1 CGPA:9.62\nSem2 CGPA:9.02\nSem3 CGPA:9.34\nSem4 CGPA:9.67\nPlaced:No";
            }
            else if (query.contains("abhishek") && query.contains("badapanda") || word.equals("2105105002"))
            {
                answer="Name: Abhisek Satapathy\nSem1 CGPA:7.24\nSem2 CGPA:7.91\nSem3 CGPA:8.56\nSem4 CGPA:8.99\nPlaced:No";
            }

            else if (query.contains("amit") && query.contains("mohanty") || word.equals("2105105003"))
            {
                answer="Name: Amit kumar Mohanty\nSem1 CGPA:8.76\nSem2 CGPA:8.65\nSem3 CGPA:9.48\nSem4 CGPA:8.88\nPlaced:No";
            }

            else if (query.contains("ananya") && query.contains("pattnaik") || word.equals("2105105004"))
            {
                answer="Name: Ananya Pattnaik\nSem1 CGPA:9.48\nSem2 CGPA:9.0\nSem3 CGPA:8.46\nSem4 CGPA:9.76\nPlaced:Yes";
            }

            else if (query.contains("asha") && query.contains("devi") || word.equals("2105105005"))
            {
                answer="Name: Asha Devi\nSem1 CGPA:9.29\nSem2 CGPA:8.42\nSem3 CGPA:7.79\nSem4 CGPA:7.87\nPlaced:No";
            }
            else if (query.contains("ashutosh") && query.contains("jena") || word.equals("2105105006"))
            {
                answer="Name: Ashutosh Jena\nSem1 CGPA:8.52\nSem2 CGPA:8.23\nSem3 CGPA:7.33\nSem4 CGPA:8.67\nPlaced:No";
            }
            else if (query.contains("bibhisan") && query.contains("mahanta") || word.equals("2105105007"))
            {
                answer="Name: Bibhisan Mahanta\nSem1 CGPA:8.81\nSem2 CGPA:8.12\nSem3 CGPA:9.15\nSem4 CGPA:9.65\nPlaced:No";
            }

            else if (query.contains("bibhuti") && query.contains("bhusan") &&query.contains("behera") || word.equals("2105105008"))
            {
                answer="Name: Bibhuti Bhusan Behera\nSem1 CGPA:9.71\nSem2 CGPA:9.09\nSem3 CGPA:7.71\nSem4 CGPA:8.45\nPlaced:No";
            }
            else if (query.contains("biswajit") && query.contains("kar") || word.equals("2105105009"))
            {
                answer="Name: Biswajit Kar\nSem1 CGPA:7.76\nSem2 CGPA:7.19\nSem3 CGPA:9.62\nSem4 CGPA:8.89\nPlaced:No";
            }
            else if (query.contains("biswasmruti") && query.contains("sahoo") || word.equals("2105105010"))
            {
                answer="Name: Biswasmruti Sahoo\nSem1 CGPA:9.0\nSem2 CGPA:8.26\nSem3 CGPA:8.85\nSem4 CGPA:8.9\nPlaced:Yes";
            }
            else if (query.contains("suraj") && query.contains("subudhi") || word.equals("2105105011"))
            {
                answer="Name: CH Suraj Subudhi\nSem1 CGPA:9.14\nSem2 CGPA:8.65\nSem3 CGPA:7.23\nSem4 CGPA:8.99\nPlaced:Yes";
            }
            else if (query.contains("chiraswati") && query.contains("mishra") || word.equals("2105105012"))
            {
                answer="Name: Chiraswati Mishra\nSem1 CGPA:8.81\nSem2 CGPA:7.79\nSem3 CGPA:8.11\nSem4 CGPA:9.56\nPlaced:No";
            }
            else if (query.contains("Debasish") && query.contains("sahoo") || word.equals("2105105013"))
            {
                answer="Name: Debasish Sahoo\nSem1 CGPA:8.86\nSem2 CGPA:8.37\nSem3 CGPA:9.89\nSem4 CGPA:9.67\nPlaced:Yes";
            }
            else if (query.contains("Deeptendu") && query.contains("sekhar") || word.equals("2105105014"))
            {
                answer="Name: Deeptendu Sekhar Sahoo\nSem1 CGPA:8.48\nSem2 CGPA:7.6\nSem3 CGPA:9.26\nSem4 CGPA:9.43\nPlaced:No";
            }
            else if (query.contains("gourav") && query.contains("rana") || word.equals("2105105015"))
            {
                answer="Name: Gourav Rana\nSem1 CGPA:8.71\nSem2 CGPA:8.05\nSem3 CGPA:8.94\nSem4 CGPA:9.12\nPlaced:Yes";
            }
            else if (query.contains("harapriya") && query.contains("jena") || word.equals("2105105016"))
            {
                answer="Name: Harapriya Jena\nSem1 CGPA:7.14\nSem2 CGPA:6.42\nSem3 CGPA:8.33\nSem4 CGPA:9.04\nPlaced:Yes";
            }
            else if (query.contains("himanshu") && query.contains("patra") || word.equals("2105105017"))
            {
                answer="Name: Himanshu patra\nSem1 CGPA:8.14\nSem2 CGPA:7.07\nSem3 CGPA:9.07\nSem4 CGPA:8.46\nPlaced:No";
            }
            else if (query.contains("Jashobanta") && query.contains("sa") || word.equals("2105105018"))
            {
                answer="Name: Jashobanta kumar sa\nSem1 CGPA:9.48\nSem2 CGPA:8.48\nSem3 CGPA:7.91\nSem4 CGPA:8.56\nPlaced:No";
            }
            else if (query.contains("jayashree") && query.contains("barik") || word.equals("2105105019"))
            {
                answer="Name: Jayashree Barik\nSem1 CGPA:9.38\nSem2 CGPA:9.02\nSem3 CGPA:8.59\nSem4 CGPA:9.09\nPlaced:Yes";
            }
            else if (query.contains("jiban") && query.contains("krusna") || word.equals("2105105020"))
            {
                answer="Name: Jiban Krushna Sahoo\nSem1 CGPA:8.14\nSem2 CGPA:7.49\nSem3 CGPA:8.78\nSem4 CGPA:9.0\nPlaced:No";
            }
            else if (query.contains("kriti") && query.contains("behera") || word.equals("2105105021"))
            {
                answer="Name: Kriti Kumar Behera\nSem1 CGPA:9.29\nSem2 CGPA:8.79\nSem3 CGPA:7.46\nSem4 CGPA:9.76\nPlaced:Yes";
            }
            else if (query.contains("lopamudra") && query.contains("behera") || word.equals("2105105022"))
            {
                answer="Name: Lopamudra Behera\nSem1 CGPA:8.62\nSem2 CGPA:8.23\nSem3 CGPA:9.54\nSem4 CGPA:9.21\nPlaced:No";
            }
            else if (query.contains("madhuchhanda") && query.contains("karan") || word.equals("2105105023"))
            {
                answer="Name: Madhuchhanda Karan\nSem1 CGPA:9.62\nSem2 CGPA:9.09\nSem3 CGPA:7.14\nSem4 CGPA:9.54\nPlaced:Yes";
            }
            else if (query.contains("malay") && query.contains("das") || word.equals("2105105024"))
            {
                answer="Name: Malay Ranjan Das\nSem1 CGPA:9.1\nSem2 CGPA:8.19\nSem3 CGPA:7.59\nSem4 CGPA:8.61\nPlaced:No";
            }
            else if (query.contains("manas") && query.contains("mohanta") || word.equals("2105105025"))
            {
                answer="Name: Manas Ranjan Mohanta\nSem1 CGPA:9.24\nSem2 CGPA:8.88\nSem3 CGPA:8.27\nSem4 CGPA:8.54\nPlaced:No";
            }
            else if (query.contains("mrutyunjaya") && query.contains("patra") || word.equals("2105105026"))
            {
                answer="Name: Mrutyunjaya Patra\nSem1 CGPA:9.48\nSem2 CGPA:9.09\nSem3 CGPA:9.33\nSem4 CGPA:9.3\nPlaced:Yes";
            }
            else if (query.contains("munu") && query.contains("singh") || word.equals("2105105027"))
            {
                answer="Name: Munu Singh\nSem1 CGPA:9.14\nSem2 CGPA:8.72\nSem3 CGPA:8.69\nSem4 CGPA:8.4\nPlaced:Yes";
            }
            else if (query.contains("narendra") && query.contains("sahu") || word.equals("2105105028"))
            {
                answer="Name: Narendra Sahu\nSem1 CGPA:9.62\nSem2 CGPA:9.16\nSem3 CGPA:9.42\nSem4 CGPA:8.9\nPlaced:No";
            }
            else if (query.contains("pitamber") && query.contains("nayak") || word.equals("2105105029"))
            {
                answer="Name: Pitambar Nayak\nSem1 CGPA:7.43\nSem2 CGPA:7.0\nSem3 CGPA:7.68\nSem4 CGPA:8.8\nPlaced:No";
            }
            else if (query.contains("prabhudatta") && query.contains("giri") || word.equals("2105105030"))
            {
                answer="Name: Prabhudutta Giri\nSem1 CGPA:9.52\nSem2 CGPA:8.81\nSem3 CGPA:9.7\nSem4 CGPA:9.35\nPlaced:No";
            }
            else if (query.contains("prasanta") && query.contains("sahoo") || word.equals("2105105031"))
            {
                answer="Name: Prasanta Kumar Sahoo\nSem1 CGPA:8.95\nSem2 CGPA:7.63\nSem3 CGPA:8.02\nSem4 CGPA:7.89\nPlaced:No";
            }
            else if (query.contains("pratyush") && query.contains("tripathy") || word.equals("2105105032"))
            {
                answer="Name: Pratyush Tripathy\nSem1 CGPA:8.81\nSem2 CGPA:8.42\nSem3 CGPA:8.43\nSem4 CGPA:9.7\nPlaced:Yes";
            }
            else if (query.contains("promod") && query.contains("naik") || word.equals("2105105033"))
            {
                answer="Name: Promod Naik\nSem1 CGPA:8.43\nSem2 CGPA:7.56\nSem3 CGPA:7.27\nSem4 CGPA:9.43\nPlaced:No";
            }
            else if (query.contains("rabindra") && query.contains("bindhani") || word.equals("2105105034"))
            {
                answer="Name: Rabindra Bindhani\nSem1 CGPA:9.48\nSem2 CGPA:8.53\nSem3 CGPA:9.11\nSem4 CGPA:8.85\nPlaced:No";
            }
            else if (query.contains("rahul") && query.contains("tudu") || word.equals("2105105035"))
            {
                answer="Name: Rahul Dev Tudu\nSem1 CGPA:7.38\nSem2 CGPA:6.4\nSem3 CGPA:8.0\nSem4 CGPA:8.78\nPlaced:No";
            }
            else if (query.contains("rakesh") && query.contains("sethi") || word.equals("2105105036"))
            {
                answer="Name: Rakesh Kumar Sethi\nSem1 CGPA:9.14\nSem2 CGPA:8.79\nSem3 CGPA:8.19\nSem4 CGPA:9.65\nPlaced:No";
            }
            else if (query.contains("ranajeet") && query.contains("kar") || word.equals("2105105037"))
            {
                answer="Name: Ranajeet Kar\nSem1 CGPA:8.05\nSem2 CGPA:7.3\nSem3 CGPA:7.04\nSem4 CGPA:9.24\nPlaced:No";
            }
            else if (query.contains("saneay") && query.contains("rath") || word.equals("2105105038"))
            {
                answer="Name: Saneay Behera\nSem1 CGPA:9.57\nSem2 CGPA:8.88\nSem3 CGPA:8.97\nSem4 CGPA:9.35\nPlaced:Yes";
            }
            else if (query.contains("sarojini") && query.contains("rath") || word.equals("2105105039"))
            {
                answer="Name: Sarojini Rath\nSem1 CGPA:9.33\nSem2 CGPA:8.79\nSem3 CGPA:9.61\nSem4 CGPA:9.56\nPlaced:No";
            }
            else if (query.contains("saswata") && query.contains("sahu") || word.equals("2105105040"))
            {
                answer="Name: Saswata Sahu\nSem1 CGPA:6.1\nSem2 CGPA:6.81\nSem3 CGPA:7.97\nSem4 CGPA:9.74\nPlaced:Yes";
            }
            else if (query.contains("satyajit") && query.contains("mohanty") || word.equals("2105105041"))
            {
                answer="Name: satyajit mohanty\nSem1 CGPA:0.0\nSem2 CGPA:0.0\nSem3 CGPA:0.0\nSem4 CGPA:0.0\nPlaced:No";
            }
            else if (query.contains("saubhagya") && query.contains("behera") || word.equals("2105105042"))
            {
                answer="Name: Saubhagya Behera\nSem1 CGPA:8.95\nSem2 CGPA:8.84\nSem3 CGPA:9.45\nSem4 CGPA:8.28\nPlaced:No";
            }
            else if (query.contains("shakti") && query.contains("sahoo") && query.contains("chandan") || word.equals("2105105043"))
            {
                answer="Name: Shakti Chandan Sahoo\nSem1 CGPA:9.0\nSem2 CGPA:8.37\nSem3 CGPA:9.03\nSem4 CGPA:9.09\nPlaced:No";
            }
            else if (query.contains("shibasmita") && query.contains("muduli") || word.equals("2105105044"))
            {
                answer="Name: Shibasmita Muduli\nSem1 CGPA:9.81\nSem2 CGPA:9.19\nSem3 CGPA:7.62\nSem4 CGPA:9.65\nPlaced:Yes";
            }

            else if (query.contains("shiv") && query.contains("mohanta") && query.contains("sankar") || word.equals("2105105045"))
            {
                answer="Name: Shiv Sankar Mohanta\nSem1 CGPA:8.95\nSem2 CGPA:8.63\nSem3 CGPA:8.51\nSem4 CGPA:9.45\nPlaced:No";
            }
            else if (query.contains("shrabani") && query.contains("patra") || word.equals("2105105046"))
            {
                answer="Name: Shrabani Patra\nSem1 CGPA:8.43\nSem2 CGPA:7.56\nSem3 CGPA:7.86\nSem4 CGPA:9.42\nPlaced:No";
            }
            else if (query.contains("siba") && query.contains("sahu") && query.contains("prasad") || word.equals("2105105047"))
            {
                answer="Name: Siba Prasad Sahu\nSem1 CGPA:8.71\nSem2 CGPA:8.02\nSem3 CGPA:9.87\nSem4 CGPA:8.35\nPlaced:No";
            }
            else if (query.contains("simran") && query.contains("sandhya") && query.contains("dash") || word.equals("2105105048"))
            {
                answer="Name: Simran Sandhya Dash\nSem1 CGPA:9.48\nSem2 CGPA:8.53\nSem3 CGPA:9.37\nSem4 CGPA:8.54\nPlaced:Yes";
            }
            else if (query.contains("soumyaranjan") && query.contains("das") || word.equals("2105105049"))
            {
                answer="Name: Soumyaranjan Das\nSem1 CGPA:9.48\nSem2 CGPA:8.86\nSem3 CGPA:7.83\nSem4 CGPA:8.9\nPlaced:Yes";
            }
            else if (query.contains("sourav") && query.contains("nayak") || word.equals("2105105050"))
            {
                answer="Name: Sourav Nayak\nSem1 CGPA:8.81\nSem2 CGPA:7.95\nSem3 CGPA:9.48\nSem4 CGPA:9.82\nPlaced:No";
            }
            else if (query.contains("subasis") && query.contains("mallick") || word.equals("2105105051"))
            {
                answer="Name: Subasis Mallick\nSem1 CGPA:9.33\nSem2 CGPA:8.95\nSem3 CGPA:8.71\nSem4 CGPA:9.74\nPlaced:Yes";
            }
            else if (query.contains("sudeep") && query.contains("panigrahi") || word.equals("2105105052"))
            {
                answer="Name: Sudeep Kumar Panigrahi\nSem1 CGPA:8.14\nSem2 CGPA:7.91\nSem3 CGPA:8.39\nSem4 CGPA:9.75\nPlaced:No";
            }
            else if (query.contains("sunanda") && query.contains("sahoo") || word.equals("2105105053"))
            {
                answer="Name: Sunanda Sahoo\nSem1 CGPA:9.62\nSem2 CGPA:8.98\nSem3 CGPA:7.51\nSem4 CGPA:9.43\nPlaced:No";
            }
            else if (query.contains("sunil") && query.contains("sahu") || word.equals("2105105054"))
            {
                answer="Name: Suniil Kumar Sahu\nSem1 CGPA:9.62\nSem2 CGPA:9.07\nSem3 CGPA:9.17\nSem4 CGPA:8.34\nPlaced:No";
            }
            else if (query.contains("sushree") && query.contains("das") || word.equals("2105105055"))
            {
                answer="Name: Sushree Das\nSem1 CGPA:8.24\nSem2 CGPA:7.6\nSem3 CGPA:9.23\nSem4 CGPA:9.54\nPlaced:No";
            }
            else if (query.contains("sushreeipsita") && query.contains("samantaray") || word.equals("2105105056"))
            {
                answer="Name: Sushreeipsita Samantaray\nSem1 CGPA:9.43\nSem2 CGPA:8.77\nSem3 CGPA:8.56\nSem4 CGPA:9.6\nPlaced:No";
            }
            else if (query.contains("suvendu") && query.contains("sahoo") || word.equals("2105105057"))
            {
                answer="Name: Suvendu Sahoo\nSem1 CGPA:7.52\nSem2 CGPA:6.53\nSem3 CGPA:9.76\nSem4 CGPA:9.92\nPlaced:Yes";
            }
            else if (query.contains("udayabhanu") && query.contains("aich") || word.equals("2105105058"))
            {
                answer="Name: Udayabhanu Aich\nSem1 CGPA:8.9\nSem2 CGPA:8.37\nSem3 CGPA:8.92\nSem4 CGPA:9.42\nPlaced:Yes";
            }
            else if (query.contains("janmejay") && query.contains("swain") || word.equals("2105105059"))
            {
                answer="Name: Janmejay Swain\nSem1 CGPA:8.76\nSem2 CGPA:8.47\nSem3 CGPA:7.08\nSem4 CGPA:9.8\nPlaced:Yes";
            }

            else if (query.contains("papu") && query.contains("sahoo") || word.equals("2105105060"))
            {
                answer="Name: Papu Sahoo\nSem1 CGPA:8.76\nSem2 CGPA:8.47\nSem3 CGPA:7.08\nSem4 CGPA:9.8\nPlaced:Yes";
            }
            else if (query.contains("satyasiba") && query.contains("sahoo") || word.equals("2105105061"))
            {
                answer="Name: Satyasiba Sahoo\nSem1 CGPA:9.24\nSem2 CGPA:8.47\nSem3 CGPA:8.88\nSem4 CGPA:9.67\nPlaced:Yes";
            }
            else if (query.contains("payal") && query.contains("sahoo") || word.equals("2105105062"))
            {
                answer="Name: Payal Sahoo\nSem1 CGPA:9.57\nSem2 CGPA:8.79\nSem3 CGPA:9.66\nSem4 CGPA:9.45\nPlaced:Yes";
            }
            else if (query.contains("bibeka") && query.contains("nanda") && query.contains("naik") || word.equals("2105105063"))
            {
                answer="Name: Bibeka nanda Naik\nSem1 CGPA:8.14\nSem2 CGPA:7.74\nSem3 CGPA:7.37\nSem4 CGPA:9.67\nPlaced:No";
            }






            //Faculty data model
            else if (query.contains("sasmita") && query.contains("mishra"))
            {
                answer="Name: Dr. Sasmita Mishra\nDesignation: Professor & hod\nQualification: Ph.d. (uu), mca (su), m.e.(su) limiste\nExpertise: Data structure, programming, language, rdbms, operating systemas, graphics";
            }
            else if (query.contains("sarojananda") && query.contains("mishra"))
            {
                answer="Name: Dr. Sarojananda Mishra\nDesignation: Professor\nQualification: Mca(s.u.), m. tech (iit delhi), ph.d.(uu), lmiste, amaima\nExpertise: Fractals and graphics, system dynamics, mis, operation research, networking, computer programming";
            }
            else if (query.contains("srinivas") && query.contains("sethi"))
            {
                answer="Name: Dr. Srinivas Sethi\nDesignation: Professor\nQualification: Ph. d., routing in manet.\nExpertise: Ad hoc network, sensor network and cognitive radio network, crowding sensing, cloud computing, bigdata and bci and cognitive science";
            }
            else if (query.contains("medini") && query.contains("srinivas"))
            {
                answer="Name: Mr. Medini Srinivas\nDesignation: Professor\nQualification: Mca (su), m.e.(su) limiste\nExpertise: Artificial intelligence, nlp, oops, software engineering";
            }
            else if (query.contains("biswanath") && query.contains("sethi"))
            {
                answer="Name: Dr. Biswanath Sethi\nDesignation: Professor\nQualification: B.e.,M.e.,Ph.d.\nExpertise: Cellular automata, pattern classification";
            }
            else if (query.contains("dillip") && query.contains("swain"))
            {
                answer="Name: Dr. Dillip ku. Swain\nDesignation: Professor\nQualification: Mca, m.tech., ph.d\nExpertise: Computational theory, ai, networking";
            }

            else if (query.contains("niroj") && query.contains("pani"))
            {
                answer="Name: Dr. Niroj ku. Pani\nDesignation: Professor\nQualification: M.tech., ph.d\nExpertise: computer networks, wireless ad hoc and sensor networks, network security, cloud computing, iot";
            }
            else if (query.contains("priyabrata") && query.contains("sahu"))
            {
                answer="Name: Mr. Priyabrata Sahu\nDesignation: Professor\nQualification: Mca, M.tech.\nExpertise: Operating system, graphics, networking, computer architecture, rdbms, operating system, graphics, networking, computer architecture";
            }
            else if (query.contains("sanjay") && query.contains("patra"))
            {
                answer="Name: Dr. Sanjaya ku. Patra\nDesignation: Professor\nQualification: Mca, M. tech, ph.d\nExpertise: Data structure, programming, language, rdbms";
            }

            else if (query.contains("anupama") && query.contains("sahu"))
            {
                answer="Name: Mrs. Anupama Sahu\nDesignation: Asst. Professor\nQualification: b. tech., m. tech., ph.d. (continuing)\nExpertise: Data base, Software engineering";
            }
            else if (query.contains("bapuji") && query.contains("rao"))
            {
                answer="Name: Mr. Bapuji Rao\nDesignation: Asst. Professor\nQualification: m.tech., ph.d. (continuing)\nExpertise: Data Mining and Data Warehousing, Java,C++,Python";
            }
            else if (query.contains("binay") && query.contains("patra"))
            {
                answer="Name: Mr. Binaya ku. Patra\nDesignation: Asst. Professor\nQualification: M.Tech., ph.d. (continuing)\nExpertise: Computer graphics, wsn";
            }
            else if (query.contains("ramesh") && query.contains("sahoo"))
            {
                answer="Name: Mr. Ramesh ku. Sahoo\nDesignation: Asst. Professor\nQualification: B. Tech, M.Tech, Ph.d (continuing)\nExpertise: Java,C++,Python,Android,Web,DBMS,Oracle";
            }
            else if (query.contains("sangita") && query.contains("pal"))
            {
                answer="Name: Dr. Sangita Pal\nDesignation: Professor\nQualification: Ph.d. in comp. sc. engg\nExpertise: Cognitive radio ad hoc network, security";
            }
            else if (query.contains("sangram") && query.contains("keshari") && query.contains("nayak"))
            {
                answer="Name: Mr. Sangram Keshari Nayak\nDesignation: Asst. Professor\nQualification: Mca, M.Tech, ph.d (continuing)\nExpertise: Programming language, DBMS, soft computing, algorithm";
            }
            else if (query.contains("subhendu") && query.contains("bhusan") && query.contains("rout"))
            {
                answer="Name: Dr. Subhendu Bhusan Rout\nDesignation: Professor\nQualification: B. tech, m.tech, ph.d\nExpertise: Bioinformatics";
            }
            else if (query.contains("supriya") && query.contains("lenka"))
            {
                answer="Name: Miss. Supriya Lenka\nDesignation: Asst. Professor\nQualification: M.Tech., Ph.d (continuing)\nExpertise: Language";
            }
            else if (query.contains("susanta") && query.contains("sahoo") && query.contains("kumar"))
            {
                answer="Name: Mr. Susanta Kumar Sahoo\nDesignation: Asst. Professor\nQualification: Mca, M.Tech., Ph.d (continuing)\nExpertise: Programming language, dbms, operating system, microprocessor";
            }
            else if (query.contains("suvendu") && query.contains("jena") && query.contains("kumar"))
            {
                answer="Name: Mr. Suvendu Kumar Jena\nDesignation: Asst. Professor\nQualification: Be(uu), M.Tech(bu), Ph.d.(continuing)\nExpertise: Systems programming, mobile computing, multidimensional database, data mining";
            }

        }
        return answer;
    }

}
