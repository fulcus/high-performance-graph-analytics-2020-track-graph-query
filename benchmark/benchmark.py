# dependencies: numpy
import argparse
import subprocess
import time
import os
from datetime import datetime

##############################
##############################

DEFAULT_NUM_BLOCKS = 1000
PROJECT_PATH = os.path.normpath(os.path.dirname(os.path.realpath(__file__))+"/../")
TARGET_DIR = PROJECT_PATH + "/target"
TARGET_JAR = TARGET_DIR + "/graph-query-jar-with-dependencies.jar"

##############################
##############################

JAR_BENCH_CMD = "java -jar {}"

def benchmark(args):
    jarBenchCMD = JAR_BENCH_CMD.format(TARGET_JAR)
    result = subprocess.run(jarBenchCMD,
                            shell=True,
                            cwd=PROJECT_PATH)
    result.check_returncode()


##############################
##############################

MVN_BUILD_CMD = "mvn clean compile assembly:single"

if __name__ == "__main__":

    parser = argparse.ArgumentParser(description="Get args for the benchmark")

    parser.add_argument("-b", "--build", action="store_true",
                        help="If present, build the project")
    parser.add_argument("-d", "--debug", action="store_true",
                        help="If present, print debug messages")
    parser.add_argument("-g", "--num_node_block", metavar="N", type=int, default=DEFAULT_NUM_BLOCKS,
                        help="Number of the nodes loaded from the file")
    parser.add_argument("-f", "--file", metavar="N", type=str, default=DEFAULT_NUM_BLOCKS,
                        help="Number of the nodes loaded from the file")

    # Parse the input arguments;
    args = parser.parse_args()

    if args.build:
        result = subprocess.run(MVN_BUILD_CMD,
                                shell=True,
                                cwd=PROJECT_PATH)
        result.check_returncode()
    else:
        print("No build")

    benchmark(args)


