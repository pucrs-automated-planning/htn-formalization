#!/usr/bin/env ruby
#-----------------------------------------------
# Run
#-----------------------------------------------
# Steps
# - Add C:\Program Files\Java\jdkVERSION\bin to your path
# - Go to your project folder and execute
#   - cd jshop2\examples\basic
#   - ruby ..\..\run.rb basic.jshop problem.jshop run
#-----------------------------------------------

# Environment
TOOLPATH = File.dirname(__FILE__)
CLASSPATH = [TOOLPATH, "#{TOOLPATH}/antlr.jar", "#{TOOLPATH}/JSHOP2.jar", '.'].join(File::PATH_SEPARATOR)

begin
  if ARGV.size < 2
    puts 'Use glue.rb domain problem [mode=run|gui] [plans=a|integer]'
  else
    # Input
    domain_file, problem_file, mode, plans = ARGV
    domain_name = File.basename(domain_file, '.*')
    problem_name = File.basename(problem_file, '.*')
    mode = 'run' if mode != 'gui'
    plans = 'a' if plans !~ /\d+/
    puts 'Input',
         "  domain file:  #{domain_file}",
         "  problem file: #{problem_file}",
         "  mode:         #{mode}",
         "  plans:        #{plans}"

    # JSHOP steps
    puts 'Translating domain...'
    system("java -cp #{CLASSPATH} JSHOP2.InternalDomain #{domain_file}")

    puts 'Translating problem...'
    system("java -cp #{CLASSPATH} JSHOP2.InternalDomain -r#{plans} #{problem_file}")

    puts 'Compiling files...'
    system("javac -cp #{CLASSPATH} -sourcepath #{TOOLPATH} -sourcepath . -d . #{TOOLPATH}/run.java #{TOOLPATH}/gui.java #{domain_name}.java #{problem_name}.java")

    puts 'Running planner...'
    system("java -cp #{CLASSPATH} #{mode} #{problem_name}")
    
    # Cleanup
    File.delete("#{domain_name}.java") if File.exist?("#{domain_name}.java")
    File.delete("#{domain_name}.txt") if File.exist?("#{domain_name}.txt")
    File.delete("#{problem_name}.java") if File.exist?("#{problem_name}.java")
    File.delete(*Dir.glob('*.class'))
  end
end