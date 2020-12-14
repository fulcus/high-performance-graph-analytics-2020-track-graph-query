import argparse
from subprocess import run, Popen, PIPE, STDOUT
import time
import os
from datetime import datetime

##############################
##############################

DEFAULT_NUM_NODES_BLOCK = 1000
RUN_PATH = os.getcwd() + "/"
PROJECT_PATH = os.path.normpath(os.path.dirname(os.path.realpath(__file__))+"/../")
TARGET_DIR = PROJECT_PATH + "/target"
TARGET_JAR = TARGET_DIR + "/graph-query-jar-with-dependencies.jar"
FILE_RELATIONSHIPS = "soc-pokec-relationships.txt"

##############################
##############################

JAR_BENCH_CMD = "java -jar {} {} {}"

def benchmark(args):
    relationships_file = RUN_PATH + args.file
    if not os.path.isfile(relationships_file):
        print("File of relationships does not exist: "+relationships_file)
        exit(1)

    print("Run benchmark!")
    jarBenchCMD = JAR_BENCH_CMD.format(TARGET_JAR, relationships_file, args.num_nodes_block)
    jar = Popen(jarBenchCMD, shell=True, stdin=PIPE, stdout=PIPE, stderr=STDOUT, close_fds=True, cwd=PROJECT_PATH)

    if jar.stdout.readline().lower() == "Dataset loaded!".lower():
        print("Dataset loaded!")
    else:
        print("Dataset not loaded!")
        exit(1)


##############################
##############################

MVN_BUILD_CMD = "mvn clean compile assembly:single"

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Get args for the benchmark")

    parser.add_argument("-b", "--build", action="store_true",
                        help="If present, build the project")
    parser.add_argument("-d", "--debug", action="store_true",
                        help="If present, print debug messages")
    parser.add_argument("-n", "--num_nodes_block", metavar="N", type=int, default=DEFAULT_NUM_NODES_BLOCK,
                        help="Number of the nodes loaded from the file")
    parser.add_argument("-f", "--file", metavar="N", type=str, default=FILE_RELATIONSHIPS,
                        help="Path of the file of relationships")

    # Parse the input arguments;
    args = parser.parse_args()

    if args.build:
        result = run(MVN_BUILD_CMD,
                                shell=True,
                                cwd=PROJECT_PATH)
        result.check_returncode()
    else:
        print("No build")

    benchmark(args)


